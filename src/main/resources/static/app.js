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
  var socket = new SockJS('https://prod.hyunn.shop/gs-guide-websocket', null, { transports: ['websocket'] });

  stompClient = Stomp.over(socket);

  userId = prompt("ID를 입력해주세요.");

  // Check if the user entered a valid ID
  if (!userId) {
    alert("유효한 ID를 입력하세요.");
    return;
  }

  stompClient.connect({}, function (frame) {
    if (frame) {
      setConnected(true);
      console.log('Connected: ' + frame);
      stompClient.subscribe('/topic/greetings', function (greeting) {
        showGreeting(JSON.parse(greeting.body).content);
      });
    } else {
      alert('연결에 실패했습니다.');
    }
  });

  $("#userIdDisplay").text("ID: " + userId);
}

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnected(false);
  console.log("Disconnected");
}

function sendName() {
  var message = $("#name").val().trim();

  // Check if the message is empty
  if (!message) {
    alert("메세지를 입력하세요.");
    return;
  }

  stompClient.send("/app/hello", {}, JSON.stringify({
    'name': message,
    'userId': userId
  }));

  // Clear the input field after sending the message
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