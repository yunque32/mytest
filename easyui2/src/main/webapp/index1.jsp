<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>静态方式(HTML方式)</title>
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
  <a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">

  <a href="javascript:void(0)" id="mb" class="easyui-menubutton"
     data-options="menu:'#mm',iconCls:'icon-edit'">Edit</a>
  <div id="mm" style="width:150px;">
      <div data-options="iconCls:'icon-undo'">Undo</div>
      <div data-options="iconCls:'icon-redo'">Redo</div>
      <div class="menu-sep"></div>
      <div>Cut</div>
      <div>Copy</div>
      <div>Paste</div>
      <div class="menu-sep"></div>
      <div data-options="iconCls:'icon-remove'">Delete</div>
      <div>Select All</div>
  </div>
      easyui</a>

  </body>
</html>
























