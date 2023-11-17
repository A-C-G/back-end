var stompClient = null;
var userId = null;

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  if (connected) {
    $("#conversation").show();
  } else {
    $("#conversation").hide();
  }
  $("#greetings").html("");
}

function connect() {
  var socket = new SockJS('/gs-guide-websocket');
  stompClient = Stomp.over(socket);

  userId = prompt("ID를 입력하세요:");

  if (!userId) {
    alert("유효한 ID를 입력하세요.");
    return;
  }

  stompClient.connect({}, function (frame) {
    setConnected(true);
    console.log('연결됨: ' + frame);
    stompClient.subscribe('/topic/greetings', function (greeting) {
      showGreeting(JSON.parse(greeting.body).content);
    });
  });

  $("#userIdDisplay").text("ID: " + userId);
}

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnected(false);
  console.log("연결 해제됨");
}

function sendName() {
  var message = $("#name").val().trim();

  if (!message) {
    alert("메세지를 입력하세요.");
    return;
  }

  stompClient.send("/app/hello", {}, JSON.stringify({
    'name': message,
    'userId': userId
  }));

  $("#name").val("");
}

function showGreeting(message) {
  $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
  $("form").on('submit', function (e) {
    e.preventDefault();
  });
  $("#connect").click(function () {
    connect();
  });
  $("#disconnect").click(function () {
    disconnect();
  });
  $("#send").click(function () {
    sendName();
  });
});
