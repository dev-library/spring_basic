<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
	#modDiv {
		width:500px;
		height:100px;
		background-color : aqua;
		border : black solid 2px;
		position : absolute;
		top:50%;
		left:50%;
		margin-top:-50px;
		margin-left:-250px;
		padding:10px;
		z-index:1000; 
	}
	#reply {
		width:450px;
	}
	
	/* uploadResult 결과물 css */
	.uploadResult {
		width : 100%;
		background-color : aqua;
	}
	.uploadResult ul {
		display : flex;
		flex-flow : row;
		justify-content : center;
		align-items : center;
	}
	.uploadResult ul li {
		list-style : none;
		padding : 10px;
		align-content : center;
		text-align : center;
	}
	.uploadResult ul li img {
		width:100%;
	}
</style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<h1 class="text text-primary">${board.bno }번글 조회중</h1>
		<div class="row">
			<div class="col-md-9">
				<input type="text" class="form-control" value="제목 : ${board.title }" />
			</div>
			<div class="col-md-3">
				<input type="text" class="form-control" value="글쓴이 : ${board.writer }" />
			</div>
		</div>
		<textarea rows="10" class="form-control">${board.content }</textarea>
		<div class="row">
			<div class="col-md-3">쓴날짜 : </div>
			<div class="col-md-3">${board.regdate }</div>
			<div class="col-md-3">수정날짜 : </div>
			<div class="col-md-3">${board.updatedate }</div>
		</div>
		
		<div class="row">
			<h3 class="text-primary">첨부파일</h3>
			<div id="uploadResult">
				<ul>
					<!-- 첨부파일이 들어갈 위치 -->
				</ul>
			</div><!-- #uploadResult -->
		</div><!-- .row -->
		
		<div class="row">
			<div class="col-md-1">
				<a href="/board/boardList?pageNum=${param.pageNum == null ? 1 : param.pageNum}&searchType=${param.searchType }&keyword=${param.keyword}" class="btn btn-success btn-sm">글목록</a>
			</div>
			<div class="col-md-1">
				<form action="/board/boardDelete" method="post">
					<input type="hidden" value="${board.bno }" name="bno" />
					<input type="hidden" name="pageNum" value="${param.pageNum}" />
					<input type="hidden" name="searchType" value="${param.searchType}" />
					<input type="hidden" name="keyword" value="${param.keyword}" />
					<input type="submit" value="삭제" class="btn btn-danger btn-sm">
				</form>
			</div>
			<div class="col-md-1">
				<form action="/board/boardUpdateForm" method="post">
					<input type="hidden" name="bno" value="${board.bno }" />
					<input type="hidden" name="pageNum" value="${param.pageNum}" />
					<input type="hidden" name="searchType" value="${param.searchType}" />
					<input type="hidden" name="keyword" value="${param.keyword}" />
					<input type="submit" value="수정" class="btn btn-warning btn-sm">
				</form>
			</div>
		</div>
	
	
	<!-- 댓글영역 -->
	<h2>댓글</h2>
	<!-- 댓글이 추가될 공간 -->
	<div class="row">
		<ul id="replies">
		
		</ul>
	</div>
	<!-- 댓글 작성 공간 -->
	<div class="row box-box-success">
		<div class="box-header">
			<h2 class="text-primary">댓글 작성</h2>
		</div><!-- header -->
		<div class="box-body">
			<strong>글쓴이</strong>
			<input type="text" id="newReplyWriter" placeholder="글쓴이" class="form-control">
			<strong>댓글내용</strong>
			<input type="text" id="newReplyText" placeholder="댓글내용" class="form-control">
		</div><!-- body -->
		<div class="box-footer">
			<button type="button" class="btn btn-success" id="replyAddBtn">글쓰기</button>
		</div>
	</div>
	
	<!-- modal은 일종의 팝업입니다.
	단, 새 창을 띄우지는 않고 css를 이용해 특정 태그가 조건부로 보이거나 안 보이도록 처리해서 
	마치 창이 새로 띄워지는것처럼 만듭니다
	따라서 눈에 보이지는 않아도 modal을 구성하는 태그 자체는 화면에 미리 적혀있어야 합니다.-->
	<div id="modDiv" style="display:none;">
		<div class="modal-title">
		</div>
		<div>
			<input type="text" id="reply">
		</div>
		<div>
			<button type="button" id="replyModBtn">수정</button>
			<button type="button" id="replyDelBtn">삭제</button>
			<button type="button" id="closeBtn">닫기</button>
		</div>
	</div>
	
	
	</div><!-- .container -->
	
	<!-- jquery cdn 가져오기 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	
	
	<!-- 여기부터 댓글 비동기 처리 자바스크립트 처리 영역 -->
	<script>
		let bno = ${board.bno};
		
		// 전체 댓글 가져오기
		function getAllList(){
			// $.getJSON은 입력한 주소에 get방식으로 요청을 넣습니다.
						// 주소   			  // 콜백함수 주소 요청으로 얻어온 json을 어떻게 처리할지
			$.getJSON("/replies/all/" + bno, function(data){
				// 문자열을 이용해 태그를 생성하거나 끼워넣을 수 있으므로
				// 빈 문자열을 만들어놓고 거기에 댓글정보를 저장해 화면에 전송
				let str = "";
				
				// 데이터를 제대로 들고왔는지 디버깅
				console.log(data);
				
				// 들고 온 데이터를 반복해서 출력하기
				//$(JSON형식데이터).each => 내부 JSON을 향상된 for문 형식으로 처리합니다.
				// 역시 내부에 콜백함수(로직이 실행되었을때 추가로 실행할 구문을 정의하기위해 파라미터로 넣는 함수)
				// 를 이용해 data를 하나하나 향상된 for문형식으로 처리할때 실행구문을 적을 수 있습니다.
				$(data).each(function(){
					// 4. 27 추가내용
					// 날짜 처리를 위해 자바스크립트의 Date 객체를 이용합니다.
					let timestamp = this.updateDate;
					let date = new Date(timestamp);
					
					let formattedTime = "게시일 : " + date.getFullYear()
										+ "년" + (date.getMonth()+1)
										+ "월" + date.getDate() + "일"
										// 시분초도 추가해보세요.
										+ "-" + date.getHours()
										+ "시" + date.getMinutes()
										+ "분" + date.getSeconds() + "초";
					
					// 하나하나 반복되는 각 데이터는 this라는 키워드로 표현됩니다.
					//console.log("----------------");
					//console.log(this);
					str += "<div class='replyLi' data-rno='" + this.rno + "'><strong>@"
						+ this.replyer + "</strong> - " + formattedTime + "<br>"
						+ "<div class='reply'>" + this.reply + "</div>"
						+ "<button type='button' class='btn btn-info'>수정/삭제</button>"
						+ "</div>";
								
				});
				// #replies사이에 끼워넣을수있도록 console.log()로 디버깅
				console.log(str);
				$("#replies").html(str);			
			});
		}		
		getAllList();// 댓글 전체 들고와서 #replies에 심어주는 로직 실행
	
		
		// 댓글 작성 
		$("#replyAddBtn").on("click", function(){
			
			// 폼이 없기때문에, input태그 내에 입력된 요소를 가져와야 합니다.
			// 버튼을 누르는 시점에, 글쓴이와 내용 내부에 적힌 문자열을 변수에 저장합니다. 
			var replyer = $("#newReplyWriter").val();
			var reply = $("#newReplyText").val();
			
			// $.ajax({내용물}); 이 기본형태
			$.ajax({
				type : 'post',
				url : '/replies',
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "POST"
				},
				dataType : 'text',
				data : JSON.stringify({
					bno : bno,
					replyer : replyer,
					reply : reply
				}),
				success : function(result){
					if(result == 'SUCCESS'){
						alert("등록 되었습니다.");
						getAllList();// 댓글 등록 성공시, 다시 목록 갱신
						// 폼 태그 비우기.
						// 힌트 : .val(넣을값);
						$("#newReplyWriter").val("");
						$("#newReplyText").val("");
					}
				}
			});		
			
		});
		
		// 이벤트 위임
		$("#replies").on("click", ".replyLi button", function(){
			// 클릭한 요소가 this이고, 현재 button에 걸렸기 때문에
			// this는 button 입니다. button의 부모가 바로 .replyLi 입니다.
			// 즉, 클릭한 버튼과 연계된 li 태그를 replytag 변수에 저장합니다. 
			var replytag = $(this).parent();
			//4월 27일 수정 : this(button)의 부모(.replyLi)가 아닌
			// 형제 태그 .reply의 내용을 대신 가져올수있도록
			// 변수 replyContent를 선언해 거기에 저장해주세요.ㅣ 
			// (hint : .siblings("요소명"); 으로 형제태그를 가져올수있습니다.)
			//var replyContent = $(this).prev().text();// button의 직전 태그인 .reply의 내용물 가져오기
			var replyContent = $(this).siblings(".reply").text();// button의 형제 중 .reply의 내용물 가져오기
			//var replyContent = $(this).parent().children(".reply").text();
			
			console.log(replytag);
			
			// 클릭한 버튼과 연계된 li태그의 data-rno에 든 값 가져와 변수 rno에 저장하기
			var rno = replytag.attr("data-rno");
			console.log(rno);
			
			// rno뿐만 아니라 본문도 가져와야함
			//var reply = replytag.text();// 내부 text를 가져옴
			//alert(rno + " : " + reply);// 내부 text와 댓글번호를 alert으로 띄움
			
			$(".modal-title").html(rno);//modal-title 부분에 글번호 입력
			$("#reply").val(replyContent);// reply 영역에 리플 내용을 기입(수정/삭제)
			$("#modDiv").show("slow");// 버튼누르면 모달 열림
		});
		
		// 모달 창 닫기 이벤트
		$("#closeBtn").on("click", function(){// #closeBtn 클릭시
			$("#modDiv").hide("slow");// #modDiv를 닫습니다
		});
		
		// 삭제로직
		$("#replyDelBtn").on("click", function(){
			let rno = $(".modal-title").html();		
			
			$.ajax({
				type : 'delete',
				url : '/replies/' + rno,
				header : {
					"X-HTTP-Method-Override" : "DELETE"
				},
				dataType : 'text',
				success : function(result) {
					console.log("result : " + result);
					if(result == 'SUCCESS'){
						alert("삭제 되었습니다.");
						$("#modDiv").hide("slow");
						getAllList();// 삭제된 댓글 반영한 새 댓글목록갱신
					}
				}
			});
		});
		
		// 수정로직
		$("#replyModBtn").on("click", function(){
			let rno = $(".modal-title").html();		
			let reply = $("#reply").val();// 댓글내용을 가져와서 넣어줘야 수정 가능
			
			$.ajax({
				type : 'put',
				url : '/replies/' + rno,
				header : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "PUT"
				},
				contentType : "application/json",// json 자료를 추가로 입력받기 때문에
				dataType : 'text',
				data: JSON.stringify({reply:reply}),
				success : function(result) {
					console.log("result : " + result);
					if(result == 'SUCCESS'){
						alert("수정 되었습니다.");
						$("#modDiv").hide("slow");
						getAllList();// 삭제된 댓글 반영한 새 댓글목록갱신
					}
				}
			});
		});
		
		(function(){
			
			$.getJSON("/board/getAttachList", {bno:bno}, function(arr){
				console.log(arr);
				var str = "";
				$(arr).each(function(i, obj){
					// 파일첨부는 boardForm.jsp에서 로직을 가져다 쓰셔도 무방합니다.
					if(!obj.fileType){
						var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
						
						str += "<li "
							+ "data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid
							+ "' data-filename='" + obj.fileName + "' data-type='" + obj.fileType
							+ "'><a href='/download?fileName=" + fileCallPath
							+ "'>" + "<img src='/resources/attach.png'>"
							+ obj.fileName + "</a>"
							+ "<span data-file=\'" + fileCallPath + "\' data-type='file'> X </span>"
							+ "</li>";
					} else {
						//str += "<li>" + obj.fileName + "</li>";
						var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
						var fileCallPathOriginal = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
						
						str += "<li "
							+ "data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid
							+ "' data-filename='" + obj.fileName + "' data-type='" + obj.fileType
							+ "'><a href='/download?fileName=" + fileCallPathOriginal
							+ "'>" + "<img src='/display?fileName="+ fileCallPath + "'>"
							+ obj.fileName + "</a>"
							+ "<span data-file=\'" + fileCallPath + "\' data-type='image'> X </span>"
							+ "</li>";
					}
					
				});
				$("#uploadResult ul").html(str);
				
			});;// end getJSON
			
		})();// 익명함수 종료
		
		
	</script>
	
	
</body>
</html>