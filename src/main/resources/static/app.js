angular.module('firmataLed', ['ngMaterial', 'mdColorPicker', 'mdColorPickerCustom', 'arduinoService']);

angular.module('firmataLed').controller('ctrl', function ($scope, Arduino) {
    $scope.colorHex = function (color) {
        return new tinycolor(color).toHex();
    }

    $scope.send = function (colorString) {
        var color = new tinycolor(colorString).toRgb();
        Arduino.setLed(color.r * color.a, color.g * color.a, color.b * color.a);
    }
})

