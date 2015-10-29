'use strict';

angular.module('arduinoFirmataLedApp')
  .controller('FadeCtrl', ["$scope", "Arduino", "$mdToast", function ($scope, Arduino,  $mdToast) {
    $scope.fade = function () {
      Arduino.setFade();
      $mdToast.show(
        $mdToast.simple()
          .content('Envoyé !')
          .position('top right')
          .hideDelay(3000)
      );
    }
  }]);
