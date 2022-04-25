<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h2>Ajax 댓글 등록 테스트</h2>
	<!-- 댓글이 추가될 공간 -->
	<ul id="replies">
	
	</ul>
	<!-- 댓글 작성 공간 -->
	<div>
		<div>
			댓글 글쓴이 <input type="text" name="replyer" id="newReplyWriter">
		</div>
		<div>
			댓글 내용 <input type="text" name="reply" id="newReplyText"> 
		</div>
		<button id="replyAddBtn">댓글 추가</button>
	</div>
	
	<!-- 위임 이해를 위한 코드(삭제예정)  -->
	<button class="test">테스트1</button>
	<button class="test">테스트2</button>
	<button class="test">테스트3</button>
	<button class="test">테스트4</button>
	
	
	
	<!-- jquery cdn 가져오기 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	
	<!-- 스크립트 태그로 자바스크립트 요청 확인 -->
	<script>
		var bno = 147474;
		
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
					// 하나하나 반복되는 각 데이터는 this라는 키워드로 표현됩니다.
					//console.log("----------------");
					//console.log(this);
					str += "<li data-rno='" + this.rno + "' class='replyLi'>"
						+ this.rno + ":" + this.reply
						+ "<button>수정/삭제</button></li>";
				});
				// #replies사이에 끼워넣을수있도록 console.log()로 디버깅
				console.log(str);
				$("#replies").html(str);			
			});
		}		
		getAllList();// 댓글 전체 들고와서 #replies에 심어주는 로직 실행
		
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
		
		//.test를 클릭하면 "테스트 클릭 감지" 라는 alert을 띄우도록 이벤트를 걸어보세요.
		$(".test").on("click", function(){
			console.log(this.html());
			// 클릭요소의 텍스트까지 같이 띄워주세요(ex : 테스트1 클릭 감지...)
			alert(this + "클릭 감지.");
		});
		
		// 이벤트 위임
		$("#replies").on("click", ".replyLi button", function(){
			// 클릭한 요소가 this이고, 현재 button에 걸렸기 때문에
			// this는 button 입니다. button의 부모가 바로 .replyLi 입니다.
			// 즉, 클릭한 버튼과 연계된 li 태그를 replytag 변수에 저장합니다.
			var replytag = $(this).parent();
			console.log(replytag);
			
			// 클릭한 버튼과 연계된 li태그의 data-rno에 든 값 가져와 변수 rno에 저장하기
			var rno = replytag.attr("data-rno");
			console.log(rno);
			
			// rno뿐만 아니라 본문도 가져와야함
			var reply = replytag.text();// 내부 text를 가져옴
			alert(rno + " : " + reply);// 내부 text와 댓글번호를 alert으로 띄움
		});
		
		
	</script>
	
	
</body>
</html>