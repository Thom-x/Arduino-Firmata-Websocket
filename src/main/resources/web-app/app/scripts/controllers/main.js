'use strict';

angular.module('arduinoFirmataLedApp')
  .controller('MainCtrl', ["$scope", "Arduino", "$mdToast", function ($scope, Arduino,  $mdToast) {
    $scope.colorHex = function (color) {
      return new tinycolor(color).toHex();
    }

    $scope.send = function (colorString) {
      var color = new tinycolor(colorString).toRgb();
      Arduino.setLed(color.r * color.a, color.g * color.a, color.b * color.a);
      $mdToast.show(
        $mdToast.simple()
          .content('Envoyé !')
          .position('top right')
          .hideDelay(3000)
      );
    }
  }]);
