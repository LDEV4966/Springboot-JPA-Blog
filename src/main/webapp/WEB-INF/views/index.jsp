<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%><!-- 한글 깨짐 방지 -->

<%@ include file="layout/header.jsp" %>
<div class="container">
  <h2>Stretched Link in Card</h2>
  <div class="card m-3">
    <div class="card-body">
      <h4 class="card-title">제목 적는 부분</h4>

      <a href="#" class="btn btn-primary stretched-link">상세보기</a>
    </div>
  </div>
  <div class="card m-3">
    <div class="card-body">
      <h4 class="card-title">제목 적는 부분</h4>

      <a href="#" class="btn btn-primary stretched-link">상세보기</a>
    </div>
  </div>
  <div class="card m-3">
    <div class="card-body">
      <h4 class="card-title">제목 적는 부분</h4>

      <a href="#" class="btn btn-primary stretched-link">상세보기</a>
    </div>
  </div>
</div>
<%@ include file="layout/footer.jsp" %>
