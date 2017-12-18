angular.module('crudApp').controller('ConfigController', ['ConfigService', '$scope',  
	function( service, $scope) {
		
		$scope.data = {
			registroMutantes: [],
			dna : ''
		}

        $scope.getFacebookData = function() {
           service.getFacebookData().then(
               function (response) {
                   console.log(response.email);
               },
               function (errResponse) {
               }
           );
        };

        $scope.fbLogin = function() {
            service.loginFB().then(
                function (response) {
                   console.log(response);
                   //$scope.getFacebookData();
                   $scope.login(response.authResponse.accessToken);
                },
                function (errResponse) {
                }
            );
        };

        $scope.login = function(token) {
            service.login(token).then(
                function (response) {
                   console.log(response);
                   $scope.getFacebookData();
                },
                function (errResponse) {
                }
            );
        };

        $scope.testSecurity = function() {
            service.testSecurity().then(
                function (response) {
                   console.log(response);
                },
                function (errResponse) {
                }
            );
        };

        $scope.logOut = function(token) {
            service.logOut(token).then(
                function (response) {
                   console.log(response);
                },
                function (errResponse) {
                }
            );
        };

        $scope.fbAsyncInit = function() {
            FB.init({
              appId: '885467881612994',
              status: true,
              cookie: true,
              xfbml: true,
              version: 'v2.4'
            });
        };

        $scope.init = function(){
            $scope.fbAsyncInit();
        };

        $scope.init();

    }


    ]);