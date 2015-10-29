'use strict';

describe('Controller: FadeCtrl', function () {

  // load the controller's module
  beforeEach(module('arduinoFirmataLedApp'));

  var FadeCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    FadeCtrl = $controller('FadeCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
