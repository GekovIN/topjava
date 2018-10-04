<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<table border=1>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach items="${meals}" var="meal">
        <tr style="${meal.exceed == true ? 'color: red' : 'color: black'}">
            <td><c:out value="${meal.dateTime.format(localDateTimeFormat)}" /></td>
            <td><c:out value="${meal.description}" /></td>
            <td><c:out value="${meal.calories}" /></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="index.html">Home</a></p>
</body>
</html>