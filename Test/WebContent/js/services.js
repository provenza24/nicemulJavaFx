angular.module('Angulardesign.Services', ['ngResource']).
		factory('UserService', function ($resource) {			
			return $resource('rest/users', {}, {
            	'query': { method: 'GET', isArray: true },
                'save': {method:'POST', isArray: false },
                'update': {method:'PUT', isArray: false },
                'delete': {method:'POST', isArray: false, headers:{'X-HTTP-Method-Override':'DELETE'}}
			});
			
		});