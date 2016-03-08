'use strict';
angular.module('Angulardesign.Controllers.User_controller',[]).
	controller("userCtrl",
		["$scope", "$location", "UserService",
		 function($scope, $location, UserService) {
			
			$scope.user = {};
			
			$scope.loadUsers = function () {
				$scope.users = UserService.query({}, function(data) {
				    // ... successfully		
					console.log("Users loaded");
				}, function(error) {
					// ... in error
					console.log("error");
				});
			};
			
			$scope.update = function (user) {
				 
				UserService.save(user, function(data) {
					console.log("save");
					$scope.loadUsers();
				 }, function(error) {
					 console.log("error");
				 });
			};
			
			$scope.reset = function () {
				console.log("reset");
				$scope.user = {};
			};
			
			$scope.loadUsers();
			
			$scope.displayUser = function (user) {
				angular.copy(user,$scope.user);
			}
		}]
);
