package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            setRoles(user);
        } else {
            updateRoles(user);
            if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
                return null;
            }
        }
        return user;
    }

    private void setRoles(User user) {
        ArrayList<Role> roles = new ArrayList<>(user.getRoles());
        jdbcTemplate.batchUpdate("INSERT INTO user_roles (role, user_id) VALUES (?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, roles.get(i).toString());
                ps.setInt(2, user.getId());
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
    }

    private void updateRoles(User user) {
        ArrayList<Role> roles = new ArrayList<>(user.getRoles());
        jdbcTemplate.batchUpdate("UPDATE user_roles SET role=? WHERE user_id=?", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, roles.get(i).toString());
                ps.setInt(2, user.getId());
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        User user = DataAccessUtils.singleResult(users);
        if (user != null)
            user.setRoles(getRole(id).get(id));
        return user;
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        User user = DataAccessUtils.singleResult(users);
        if (user != null) {
            int userId = user.getId();
            user.setRoles(getRole(userId).get(userId));
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        Map<Integer, EnumSet<Role>> roles = getAllRoles();
        users.forEach(u -> u.setRoles(roles.get(u.getId())));
        return users;
    }

    private Map<Integer, EnumSet<Role>> getRole(int userId) {
        Map<Integer, EnumSet<Role>> singleRoleMap = jdbcTemplate.query("SELECT * FROM USER_ROLES WHERE USER_ID=?", new ResultSetExtractor<Map<Integer, EnumSet<Role>>>() {
            @Override
            public Map<Integer, EnumSet<Role>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                Map<Integer, EnumSet<Role>> rolesMap = getRolesMap(rs);
                return Collections.singletonMap(userId, rolesMap.get(userId));
            }
        }, userId);

        return singleRoleMap;
    }

    private Map<Integer, EnumSet<Role>> getAllRoles() {
        return jdbcTemplate.query("SELECT * FROM USER_ROLES ORDER BY USER_ID", this::getRolesMap);
    }

    private Map<Integer, EnumSet<Role>> getRolesMap(ResultSet rs) throws SQLException {
        Map<Integer, EnumSet<Role>> roleHashMap = new HashMap<>();
        while (rs.next()) {
            int userId = rs.getInt("user_id");
            String role = rs.getString("role");
            roleHashMap.computeIfAbsent(userId, id -> EnumSet.noneOf(Role.class)).add(Role.valueOf(role));
        }
        return roleHashMap;
    }
}
