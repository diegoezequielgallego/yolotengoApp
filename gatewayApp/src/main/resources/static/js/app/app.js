var app = angular.module('crudApp',['ui.router','ngStorage','oc.lazyLoad']);

app.constant('urls', {
    BASE: 'http://localhost:8084/gateway',
    USER_SERVICE_API : 'http://localhost:8084/gateway/api/'
});

app.config(['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider',
    function($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {
		
		//genero el modulo principal de angular haciedno que responda al app/
		//retornando la pagina list con el controlador Config y su service inyectado usando la lib lazyLoad
        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/list',
                controller:'ConfigController',
                controllerAs:'ctrl',
                resolve: {
                	 lazy: ['$ocLazyLoad', function($ocLazyLoad) {
                         return $ocLazyLoad.load([{
                             name: 'crudApp',
                             files: [
                            	 'js/app/ConfigController.js',
                                 'js/app/ConfigService.js' 
                             ]
                         }]);
                     }]
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);

