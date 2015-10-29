'use strict';


angular.module('arduinoService', [])
    .factory('Arduino', [function() {
            var Service = {}
            var ProtoBuf = dcodeIO.ProtoBuf;
            ProtoBuf.loadProtoFile("proto/led.proto", function(err, builder) {
                var Arduino = builder.build("Arduino");
                var Led = Arduino.Led;

                var socket = new SockJS('/led/');
                var stompClient = null;
                stompClient = Stomp.over(socket);
                stompClient.debug = null;
                stompClient.connect({}, function(frame) {
                    //console.log('Connected: ' + frame);
                    stompClient.subscribe('/result/led', function(result){
                        console.log(result.body);
                    });
                });

                Service.setLed = function(red, green, blue) {
                    var ledCmd = new Led({red :  parseInt(red), green : parseInt(green), blue : parseInt(blue)});
                    var buffer = ledCmd.encode64();
                    //console.log(buffer);
                    stompClient.send("/app/led", {}, buffer);
                }

                Service.setFade = function() {
                    stompClient.send("/app/fade", {});
                }
            });

            return Service;
        }
    ]);
