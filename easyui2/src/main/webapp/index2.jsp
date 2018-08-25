<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>动态方式(JS方式)</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- 引入EasyUI的2个CSS和2个JS文件 -->
	<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">   
	<link rel="stylesheet" type="text/css" href="themes/icon.css">   
	<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>   
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>  
  </head>
  
  <body>
  	<div id="win"></div>

  
	<input type="button" value="点击后，弹出窗口" onclick="fn()"/>
	
	<script type="text/javascript">
		//创建EasyUI组件之动态方式
		function fn(){
			//语法：JQ选择器.组件名({JSON对象});
			$("#win").window({
				iconCls:"icon-add",
				modal:true,
				collapsible:false,
        		minimizable:false,
        		maximizable:false,
        		draggable:false,
        		resizable:false,
        		width:300,
        		height:300,
        		left:100,
        		top:100,
        		title:"我的窗口",
        		content:"这里的内容，会出现在窗口中"
			});
		}
	</script>	
  </body>
</html>









