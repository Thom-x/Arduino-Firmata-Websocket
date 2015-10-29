'use strict';


angular.module('arduinoFirmataLedApp', [
  'ngCookies',
  'ngResource',
  'ngAria',
  'ngTouch',
  'ngCookies',
  'ngAnimate',
  'ngMessages',
  'ngSanitize',
  'ngMaterial',
  'mdColorPicker',
  'mdColorPickerCustom',
  'arduinoService',
  'ui.router'
  ])
.config(function ($stateProvider, $urlRouterProvider) {
    //delete $httpProvider.defaults.headers.common['X-Requested-With'];
    $urlRouterProvider.otherwise('/color');
    $stateProvider
    .state('color', {
      url: '/color',
      views: {
        '': { 
          templateUrl: 'views/main.html',
          controller:'MainCtrl'
        },
        'menu': { 
          templateUrl: 'views/menu.html',
          controller:'MenuCtrl'
        },
        'header': { 
          templateUrl: 'views/header.html',
          controller:'HeaderCtrl'
        },
      }
    })
    .state('fade', {
      url: '/fade',
      views: {
        '': { 
          templateUrl: 'views/fade.html',
          controller:'FadeCtrl'
        },
        'menu': { 
          templateUrl: 'views/menu.html',
          controller:'MenuCtrl'
        },
        'header': { 
          templateUrl: 'views/header.html',
          controller:'HeaderCtrl'
        },
      }
    })
  })




