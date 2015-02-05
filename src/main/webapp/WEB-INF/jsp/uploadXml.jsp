<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="<spring:url value="/resources/dist/css/additional.css" htmlEscape="true"/>"
          type="text/css"/>
    <link rel="stylesheet" href="<spring:url value="/resources/dist/css/bootstrap.min.css" htmlEscape="true"/>"
          type="text/css"/>
    <link rel="stylesheet" href="<spring:url value="/resources/dist/css/bootstrap-theme.min.css" htmlEscape="true"/>"
          type="text/css"/>
    <title></title>
</head>
<body>
<div class="wrapper co1">
    <div id="upload">
        <form method="POST" action="/uploadXml" enctype="multipart/form-data">
            File to upload: <input type="file" name="file"><br/>
            <input type="submit" value="Upload"> Press here to upload the file!
        </form>
    </div>
</div>

</body>
</html>
