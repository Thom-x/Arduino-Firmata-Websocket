'use strict';

angular.module('arduinoFirmataLedApp')
  .controller('FadeCtrl', ["$scope", "Arduino", "$mdToast", function ($scope, Arduino,  $mdToast) {
    $scope.setFade = function() {
      Arduino.setFade($scope.fadeType);
      $mdToast.show(
        $mdToast.simple()
          .content('Envoyé !')
          .position('top right')
          .hideDelay(3000)
      );
    }
  }]);
