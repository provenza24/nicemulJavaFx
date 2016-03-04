'use strict';
angular.module('Angulardesign.Controllers.Console_controller',[]).
	controller("consoleCtrl",
		["$scope", "$location", "ConsoleService",
		 function($scope, $location, ConsoleService) {
			
			$scope.consoles = ConsoleService.query({}, function(data) {
			    // ... successfully		
				console.log("bonjour");
			}, function(error) {
				// ... in error
				console.log("error");
			});		
			
		}]
);