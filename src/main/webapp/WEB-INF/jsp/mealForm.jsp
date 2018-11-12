<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring_msg" uri="http://www.springframework.org/tags" %>

<%--Выставляем message в зависимости от формы:--%>
<spring_msg:message code="mealForm.create" var="labelCreate"/>
<spring_msg:message code="mealForm.update" var="labelUpdate"/>
<spring_msg:eval expression="meal.id == null ? labelCreate : labelUpdate" var="formTitle"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <h2>${formTitle}</h2>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="${pageContext.request.contextPath}/meals/save">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring_msg:message code="mealForm.dateTime"/></dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring_msg:message code="mealForm.description"/></dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring_msg:message code="mealForm.calories"/></dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit"><spring_msg:message code="mealForm.save"/></button>
        <button onclick="window.history.back()" type="button"><spring_msg:message code="mealForm.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
