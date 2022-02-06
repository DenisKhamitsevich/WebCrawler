<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <link rel="stylesheet" href="./Styles/IndexPageStyles.css">
    <title>Web Crawler</title>
</head>
<body>
<h1>
    Web Crawler
</h1>
<form method="post" action="statistics">
    <input name="url" placeholder="Starting URL" required>
    <input name="terms" placeholder="Terms (put semicolons between terms)" required>
    <input name="pageLimit" placeholder="Max amount of pages" required>
    <input name="levelLimit" placeholder="Link depth" required>
    <input class="submitButton"type="submit" value="Search">
</form>

</body>
</html>