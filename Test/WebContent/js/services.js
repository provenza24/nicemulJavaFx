angular.module('Angulardesign.Services', ['ngResource']).
		factory('ConsoleService', function ($resource) {			
			return $resource('rest/consoles', {}, {
            	'query': { method: 'GET', isArray: true },
                'save': {method:'POST', isArray: false },
                'update': {method:'PUT', isArray: false },
                'delete': {method:'POST', isArray: false, headers:{'X-HTTP-Method-Override':'DELETE'}}
			});
			
		}).
		factory('ConsoleRomsService', function ($resource) {			
			return $resource('rest/consoles/:id/roms', {id: '@id'}, {
            	'query': { method: 'GET', isArray: true },
                'save': {method:'POST', isArray: false },
                'update': {method:'PUT', isArray: false },
                'delete': {method:'POST', isArray: false, headers:{'X-HTTP-Method-Override':'DELETE'}}
			});
			
		});
