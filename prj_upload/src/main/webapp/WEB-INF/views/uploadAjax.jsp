<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/resources/uploadAjax.css">
<link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css">
<script src="/resources/jquery-3.6.0.min.js"></script>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1 class="text-primary">Upload with Ajax</h1>
	
	<div class="uploadDiv">
		<input type="file" name="uploadFile" multiple>
	</div>
	
	<div class="uploadResult">
		<ul>
			<!-- 업로드된 파일이 들어갈 자리 -->
			
		</ul>
	</div>
	
	<button id="uploadBtn">Upload</button>
	
	<script>
		$(document).ready(function(){
								//.(아무문자한개)*(갯수제한없음) \.(.을 아무문자1개가 아닌. 자체로 쓸때)
								//"(.*?)@(.*?)\.(com|net|기타확장자)$" <- 이메일 검증 정규표현식
			var regex= new RegExp("(.*?)\.(exe|sh|zip|alz)$");
			var maxSize = 5242880; // 5mb
			
			function checkExtension(fileName, fileSize){
				if(fileSize >= maxSize){
					alert("파일 사이즈 초과");
					return false;
				}
				
				if(regex.test(fileName)){
					alert("해당 종류 파일은 업로드할 수 없습니다.");
					return false;
				}
				return true;
			}
			
			// 첨부가 안 된 상태의 .uploadDiv를 깊은복사해서
			// 첨부 완료 후에 안 된 시점의 .uploadDiv로 덮어씌우기
			var cloneObj = $(".uploadDiv").clone();
			
			
			$('#uploadBtn').on("click", function(e){
				
				var formData = new FormData();
				
				var inputFile = $("input[name='uploadFile']");
				
				var files = inputFile[0].files;
				
				console.log(files);
				
				// 파일 데이터를 폼에 집어넣기
				for(var i = 0; i < files.length; i++){
					
					if(!checkExtension(files[i].name, files[i].size)){
						return false;
					}

					formData.append("uploadFile", files[i]);
					
				}

				
				$.ajax({
					url: '/uploadAjaxAction',
					processData: false,
					contentType: false,
					data: formData,
					type:"POST",
					dataType:"json",
					success: function(result){
						console.log(result);
						
						showUploadedFile(result);
						
						// 업로드 성공시 미리 복사해둔 .uploadDiv 로 덮어씌워서 첨부파일이 없는 상태로 되돌려놓기
						$(".uploadDiv").html(cloneObj.html());
						alert("Uploaded");
					}
				}); // ajax
				
			});// onclick uploadBtn
			
			var uploadResult = $(".uploadResult ul");
			
			function showUploadedFile(uploadResultArr){
				var str = "";
				
				$(uploadResultArr).each(function(i, obj){
					if(!obj.image){
						var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
						str += "<li><a href='/download?fileName=" + fileCallPath + "'>" + "<img src='/resources/attach.png'>" + obj.fileName + "</a>"
							+ "<span data-file=\'" + fileCallPath + "\' data-type='file'> X </span>"						
							+ "</li>";
					} else {
						//str += "<li>" + obj.fileName + "</li>";
						var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
						var fileCallPathOriginal = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
						
						str += "<li><a href='/download?fileName=" + fileCallPathOriginal + "'>" + "<img src='/display?fileName=" + fileCallPath + "'>" + obj.fileName + "</a>"
							+ "<span data-file=\'" + fileCallPath + "\' data-type='image'> X </span>"
							+ "</li>";
					}
				});
				uploadResult.append(str);
			}// showUploadedfile
			
		});
		
		$(".uploadResult").on("click", "span", function(e){
			var targetFile = $(this).data("file");
			var type = $(this).data("type");
			
			var targetLi = $(this).closest("li");
			
			$.ajax({
				url: '/deleteFile',
				data: {fileName: targetFile, type:type},
				dataType : 'text',
				type: 'POST',
				success : function(result){
					alert(result);
					targetLi.remove();
				}
			});// ajax
		});	// click span
		
		
		
	</script>
	
</body>
</html>