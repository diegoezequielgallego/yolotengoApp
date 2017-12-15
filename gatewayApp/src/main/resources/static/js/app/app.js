var app = angular.module('crudApp',['ui.router','ngStorage','oc.lazyLoad']);

app.constant('urls', {
    BASE: 'http://localhost:8084/gateway',
    USER_SERVICE_API : 'http://localhost:8084/gateway/api/'
});

app.config(['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider',
    function($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $stateProvider
            .state('/gateway', {
                url: '/',
                templateUrl: 'templates/list.html',
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

