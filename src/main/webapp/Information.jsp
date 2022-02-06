<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result</title>
    <link rel="stylesheet" href="./Styles/InformationPageStyles.css">
</head>
<body>
<p class="timeParagraph">Time: ${time} sec</p>
<p class="topPagesParagraph">Top 10 pages by total hits</p>
<table>
    <tr>
        <td>URL</td>
        <c:forEach var="termValue" items="${termsList}">
            <td>${termValue}</td>
        </c:forEach>
        <td>total</td>
    </tr>

    <c:forEach var="stat" items="${SortedResult}">
        <tr>
            <td class="url-td">${stat.value}</td>

            <c:if test="${stat.key==null}">
                <td>Connection error!</td>
            </c:if>

            <c:if test="${stat.key!=null}">
                <c:forEach var="term" items="${stat.key}">
                    <td>${term.value}</td>
                </c:forEach>
            </c:if>
        </tr>
    </c:forEach>
</table>
<p class="allPagesParagraph">All stat report</p>
<table>
    <tr>
        <td>URL</td>
        <c:forEach var="termValue" items="${termsList}">
            <td>${termValue}</td>
        </c:forEach>
        <td>total</td>
    </tr>

    <c:forEach var="stat" items="${result}">
        <tr>
            <td class="url-td">${stat.key}</td>

            <c:if test="${stat.value==null}">
                <td>Connection error!</td>
            </c:if>

            <c:if test="${stat.value!=null}">
                <c:forEach var="term" items="${stat.value}">
                    <td>${term.value}</td>
                </c:forEach>
            </c:if>

        </tr>
    </c:forEach>
</table>


</body>
</html>
