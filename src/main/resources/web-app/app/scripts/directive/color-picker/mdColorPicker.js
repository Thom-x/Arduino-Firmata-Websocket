'use strict';

angular.module('mdColorPickerCustom', [])
	.factory('mdColorPickerHistory', ['$injector', function( $injector ) {

		var history = [];
		var strHistory = [];

		var $cookies = false;
		try {
			$cookies = $injector.get('$cookies');
		} catch(e) {

		}

		if ( $cookies ) {
			var tmpHistory = $cookies.getObject( 'mdColorPickerHistory' ) || [];
			for ( var i = 0; i < tmpHistory.length; i++ ) {
				history.push( tinycolor( tmpHistory[i] ) );
				strHistory.push( tmpHistory[i] );
			}
		}

		var length = 40;

		return {
			length: function() {
				if ( arguments[0] ) {
					length = arguments[0];
				} else {
					return history.length;
				}
			},
			add: function( color ) {
				for( var x = 0; x < history.length; x++ ) {
					if ( history[x].toRgbString() === color.toRgbString() ) {
						history.splice(x, 1);
						strHistory.splice(x, 1);
					}
				}

				history.unshift( color );
				strHistory.unshift( color.toRgbString() );

				if ( history.length > length ) {
					history.pop();
					strHistory.pop();
				}
				if ( $cookies ) {
					$cookies.putObject('mdColorPickerHistory', strHistory );
				}
			},
			get: function() {
				return history;
			},
			reset: function() {
				history = [];
				strHistory = [];
				if ( $cookies ) {
					$cookies.putObject('mdColorPickerHistory', strHistory );
				}
			}
		};
	}])
	.directive('mdColorPickerCustom', [ '$timeout', 'mdColorPickerHistory', function( $timeout, colorHistory ) {

		return {
			templateUrl: "views/directive/color-picker/mdColorPickerCustom.tpl.html",
			scope: {
				value: '=?',
				onSelected: '=',
				type: '@',
				label: '@',
				icon: '@',
				default: '@',
				random: '@',
				openOnInput: '@'
			},
			controller: ['$scope', '$element', '$mdDialog', function( $scope, $element, $mdDialog ) {
				$scope.ok = function(color) {
					colorHistory.add( new tinycolor( color ) );
					$scope.value = new tinycolor( color ).toHex();
					$scope.onSelected(new tinycolor( color ).toHex());
				}
			}],
			compile: function( element, attrs ) {
				//attrs.value = attrs.value || "#ff0000";
				attrs.type = attrs.type !== undefined ? attrs.type : 0;
			}
		};
	}])