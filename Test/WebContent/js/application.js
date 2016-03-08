var AppAngulardesign = angular.module('Angulardesign',
		['ui.bootstrap',
		'ngRoute',		
		'Angulardesign.Services',
		'Angulardesign.Controllers.Global_controller',
		'Angulardesign.Controllers.User_controller'
		])
   .config(['$routeProvider', function($routeProvider) {
   $routeProvider.
   	  when('/home', {
    	  templateUrl:'views/home.htm', 
    	  controller:'globalCtrl'
      }).when('/users', {
    	  templateUrl:'views/users.htm', 
    	  controller:'userCtrl'
      }).
      otherwise({redirectTo: '/home'});
}]).config(['$httpProvider', function($httpProvider) {    
    var interceptor = ['$rootScope', '$q', function($rootScope, $q) {
      function success(response) {
        return response;
      }
 
      function error(response) {
        if ( (response.status === 403) || (response.status === 401) ) {
        	window.location = "./index.html#/403";
        }
        
        // otherwise, default behaviour
        return $q.reject(response);
      }
 
      return function(promise) {
        return promise.then(success, error);
      };
 
    }];
    $httpProvider.responseInterceptors.push(interceptor);
}]);
