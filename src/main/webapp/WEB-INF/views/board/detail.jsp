<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ include file="../layout/header.jsp" %>
<div class="container">
  <button id="btn-go-back" onclick="goBack();" class="btn btn-secondary">
    돌아가기
  </button>
  <c:if test="${board.user.id==principal.user.id}">
    <!-- <a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a> -->
    <button id="btn-go-revise" onclick="goRevise();" class="btn btn-warning">
      수정
    </button>
    <button id="btn-delete" class="btn btn-danger">삭제</button>
  </c:if>
  <br /><br />
  <div>
    글 번호 :
    <span id="board-id">
      <i>${board.id}</i>
      <br />
    </span>
    글 작성자 :
    <span> <i>${board.user.username}</i></span>
  </div>
  <br />
  <div>
    <h3>${board.title}</h3>
  </div>
  <hr />
  <div>
    <div>${board.content}</div>
  </div>
  <hr />
</div>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>
