'use strict';


angular.module('arduinoService', [])
  .factory('Arduino', [function () {
    var Service = {}
    var ProtoBuf = dcodeIO.ProtoBuf;
    ProtoBuf.loadProtoFile("proto/led.proto", function (err, builder) {
      var Arduino = builder.build("Arduino");
      var LedCommand = Arduino.LedCommand;
      var FadeCommand = Arduino.FadeCommand;

      //var socket = new SockJS('http://127.0.0.1:8080/led/');
      var socket = new SockJS('/led/');
      var stompClient = null;
      stompClient = Stomp.over(socket);
      stompClient.debug = null;
      stompClient.connect({}, function (frame) {
        stompClient.subscribe('/result/led', function (result) {
          //console.log(result.body);
        });
        stompClient.subscribe('/result/fade', function (result) {
          //console.log(result.body);
        });
      });

      Service.setLed = function (red, green, blue) {
        var ledCmd = new LedCommand({red: parseInt(red), green: parseInt(green), blue: parseInt(blue)});
        var buffer = ledCmd.encode64();
        stompClient.send("/app/led", {}, buffer);
      }

      Service.setFade = function (type) {
        var fadeCommand = new FadeCommand({type: type});
        var buffer = fadeCommand.encode64();
        stompClient.send("/app/fade", {}, buffer);
      }
    });

    return Service;
  }
  ]);
