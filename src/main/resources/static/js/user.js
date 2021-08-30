let index = {
  init: function () {
    $("#btn-save").on("click", () => {
      //function(){}이 아니라 ()=>{} 를 쓴 이유는 this 를 바인딩 하기 위해서
      this.save();
    });
    $("#btn-update").on("click", () => {
      //function(){}이 아니라 ()=>{} 를 쓴 이유는 this 를 바인딩 하기 위해서
      this.update();
    });
  },

  save: function () {
    //lert("asd");
    let data = {
      username: $("#username").val(),
      password: $("#password").val(),
      email: $("#email").val(),
    };
    //ajax 호출은 디폴트로 비동기 처리이다.
    //ajax 통신을 통해서 위의 데이터를 json으로 변경하여 insert 요청
    //ajax는 통신을 성공하고 나서 json을 리턴해주면 자동으로 javascript object로 리턴해준다.
    $.ajax({
      //회원가입 수행 요청
      type: "POST",
      url: "/auth/joinProc",
      data: JSON.stringify(data), //http bdoy 데이터
      contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
      dataType: "json", // 요청을 서버로 해서 응답이 왔을 때,  dataType이 json이라면, javascript object로 변경해준다.
    })
      .done(function (res) {
        //성공시
        alert("회원가입이 완료되었습니다.");
        location.href = "/";
      })
      .fail(function (error) {
        //실패시
        alert(JSON.stringify(error));
      });
  },
  update: function () {
    let data = {
      id: $("#id").val(),
      username: $("#username").val(),
      password: $("#password").val(),
      email: $("#email").val(),
    };
    $.ajax({
      //회원가입 수행 요청
      type: "PUT",
      url: "/user",
      data: JSON.stringify(data), //http bdoy 데이터
      contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
      dataType: "json", // 요청을 서버로 해서 응답이 왔을 때,  dataType이 json이라면, javascript object로 변경해준다.
    })
      .done(function (res) {
        //성공시
        alert("수정이 완료되었습니다.");
        location.href = "/";
      })
      .fail(function (error) {
        //실패시
        alert(JSON.stringify(error));
      });
  },
};

index.init();
