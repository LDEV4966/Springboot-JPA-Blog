let index = {
  init: function () {
    $("#btn-save").on("click", () => {
      this.save();
    });
    $("#btn-delete").on("click", () => {
      this.deleteById();
    });
    $("#btn-update").on("click", () => {
      this.update();
    });
    $("#btn-reply-save").on("click", () => {
      this.replySave();
    });
  },

  save: function () {
    //lert("asd");
    let data = {
      title: $("#title").val(),
      content: $("#content").val(),
    };
    $.ajax({
      //회원가입 수행 요청
      type: "POST",
      url: "/api/board",
      data: JSON.stringify(data), //http bdoy 데이터
      contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
      dataType: "json", // 요청을 서버로 해서 응답이 왔을 때,  dataType이 json이라면, javascript object로 변경해준다.
    })
      .done(function (res) {
        //성공시
        alert("글쓰기 완료되었습니다.");
        location.href = "/";
      })
      .fail(function (error) {
        //실패시
        alert(JSON.stringify(error));
      });
  },
  update: function () {
    let id = $("#board-id").val();
    let data = {
      title: $("#title").val(),
      content: $("#content").val(),
    };
    $.ajax({
      type: "PUT",
      url: "/api/board/" + id,
      data: JSON.stringify(data), //http bdoy 데이터
      contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
      dataType: "json", // 요청을 서버로 해서 응답이 왔을 때,  dataType이 json이라면, javascript object로 변경해준다.
    })
      .done(function (res) {
        //성공시
        let url_parse = getURLParams(location.search);
        let page = url_parse["page"];
        alert("글 수정이 완료되었습니다.");
        location.href = "/board/" + id + "?page=" + page + "&board=" + id;
      })
      .fail(function (error) {
        //실패시
        alert(JSON.stringify(error));
      });
  },
  deleteById: function () {
    var id = $("#board-id").text().trim();
    $.ajax({
      type: "DELETE",
      url: "/api/board/" + id,
      dataType: "json", // 요청을 서버로 해서 응답이 왔을 때,  dataType이 json이라면, javascript object로 변경해준다.
    })
      .done(function (res) {
        //성공시
        alert("삭제 완료되었습니다.");
        location.href = "/";
      })
      .fail(function (error) {
        //실패시
        alert(JSON.stringify(error));
      });
  },
  replySave: function () {
    let data = {
      userId: $("#userId").val(),
      boardId: $("#boardId").val(),
      content: $("#reply-content").val(),
    };
    $.ajax({
      //회원가입 수행 요청
      type: "POST",
      url: `/api/board/${data.boardId}/reply`,
      data: JSON.stringify(data), //http bdoy 데이터
      contentType: "application/json; charset=utf-8", // body 데이터가 어떤 타입인지 (MIME)
      dataType: "json", // 요청을 서버로 해서 응답이 왔을 때,  dataType이 json이라면, javascript object로 변경해준다.
    })
      .done(function (res) {
        //성공시
        let url_parse = getURLParams(location.search);
        let page = url_parse["page"];
        alert("댓글작성이 완료되었습니다.");
        location.href = `/board/${data.boardId}` + "?page=" + page;
      })
      .fail(function (error) {
        //실패시
        alert(JSON.stringify(error));
      });
  },
  replyDelete: function (boardId, replyId) {
    $.ajax({
      type: "DELETE",
      url: `/api/board/${boardId}/reply/${replyId}`,
      dataType: "json", // 요청을 서버로 해서 응답이 왔을 때,  dataType이 json이라면, javascript object로 변경해준다.
    })
      .done(function (res) {
        //성공시
        let url_parse = getURLParams(location.search);
        let page = url_parse["page"];
        alert("댓글삭제가 완료되었습니다.");
        location.href = `/board/${boardId}` + "?page=" + page;
      })
      .fail(function (error) {
        alert(JSON.stringify(error));
      });
  },
};

index.init();

function getURLParams(url) {
  var result = {};
  url.replace(/[?&]{1}([^=&#]+)=([^&#]*)/g, function (s, k, v) {
    result[k] = decodeURIComponent(v);
  });
  return result;
}

function goBack() {
  let url_parse = getURLParams(location.search);
  let page = url_parse["page"];
  if (page) {
    url = "/?page=" + page;
    location.href = url;
  } else {
    location.href = "/";
  }
}
function goRevise() {
  let url_parse = getURLParams(location.search);
  console.log(url_parse);
  let boardId = url_parse["board"];
  let page = url_parse["page"];
  if (page && boardId) {
    url =
      "/board/" + boardId + "/updateForm?page=" + page + "&board=" + boardId;
    location.href = url;
  } else {
    location.href = "/";
  }
}
