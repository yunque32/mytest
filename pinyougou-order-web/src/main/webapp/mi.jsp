<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script type="text/javascript">
    function A() {
        A.prototype.say=function(){
            console.log("aaa");
            alert("aaa");
        }
    }
    var a=new A();
    a.prototype.say=function () {
        console.log("bbb");
        alert("bbb")
    }
    a.say();

</script>
<body>
shenming
</body>
</html>
