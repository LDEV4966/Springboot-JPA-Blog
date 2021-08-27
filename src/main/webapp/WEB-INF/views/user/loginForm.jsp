<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%><!-- 한글 깨짐 방지 -->

<%@ include file="../layout/header.jsp" %>
<div class="container">
  <form action="#" method="post">
    <div class="form-group">
      <label for="email">Username</label>
      <input
        type="text"
        class="form-control"
        placeholder="Enter Username"
        id="username"
        name="username"
      />
    </div>

    <div class="form-group">
      <label for="password">Password</label>
      <input
        type="password"
        class="form-control"
        placeholder="Enter password"
        id="password"
        name="password"
      />
    </div>
    <div class="form-group form-check">
      <label class="form-check-label">
        <input class="form-check-input" type="checkbox" name="remember" />
        Remember me
      </label>
    </div>
    <button id="btn-login" class="btn btn-primary">로그인</button>
  </form>
</div>
<!-- <script src="/js/user.js"></script> -->
<%@ include file="../layout/footer.jsp" %>
