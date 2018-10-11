<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Add new meal</title>
</head>
<body>

<jsp:useBean id="meal" scope="request" class="ru.javawebinar.topjava.model.meal_model.Meal"/>
<form method="POST" action='meals' name="frmAddMeal">
    Meal ID :
    <br />
    <input type="text" readonly="readonly" name="id"
                     value="<c:out value="${insert == true ? 'Will be added automatically' : meal.id}" />" />
    <br />
    Date :
    <br />
    <input
        type="datetime-local" name="dateTime"
        value="<c:out value="${meal.dateTime}" />" />
    <br />
    Description :
    <br />
    <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />" />
    <br />
    Calories :
    <br />
    <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />" />
    <br />
    <br />
    <input type="submit" value="Submit" />
</form>
</body>
</html>
