<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<link rel="stylesheet" href="/static/css/bootstrap.min.css" />
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-5">
				<!-- 
					1> 文件上传的method必须是post
					2> 设置enctype属性值为multipart/form-data
					3> input的type属性必须为file
				 -->
				<form action="/fileupload" method="post" enctype="multipart/form-data">
					
					<legend><h3>文件上传</h3></legend>
					
					<div class="form-group">						
						<h5>Notes about the file:</h5><input type="text" name="des" class="form-control"/>
						<h5>File to upload: </h5><input type="file" name="file"/> <br />
						<button class="btn btn-primary">保存</button>
					</div>
				</form>
				
				
			</div>
		</div>
	</div>
</body>
</html>