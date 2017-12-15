angular.module('crudApp').controller('ConfigController', ['ConfigService', '$scope',  
	function( service, $scope) {
		
		//se crean todas las variables y objetos dentro de 
		//otro asi a la hora de limpiarlos es mas optimo angular
		$scope.data = {
			registroMutantes: [],
			dna : ''
		}

        $scope.getMyLastName = function() {
           service.getFacebookData().then(
               function (response) {
                   console.log(response.last_name);
               },
               function (errResponse) {
               }
           );
        };

        $scope.fbLogin = function() {
            service.loginFB().then(
                function (response) {
                   console.log(response.last_name);
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


        $scope.submit = function() {
            console.log('Submitting');
            $scope.data.buttonRunDisable = true;
            $scope.data.successMessage = 'Corriendo ...';

            //var dna = ["ATGGGG", "CATTGA", "TTATGA", "TGAAGA", "CCCCTA", "TCACTG"];
            var dna = $scope.data.dna.split(',');
            
            service.isMutant({dna: dna}).then(
                function (response) {
                	$scope.data.successMessage = 'Es mutante';
                	$scope.data.errorMessage='';
                    $scope.data.buttonRunDisable = false;
                },
                function (errResponse) {
                	$scope.data.errorMessage = 'Alerta: ' + errResponse.data.errorMessage;
                	$scope.data.successMessage='';
                	$scope.data.buttonRunDisable = false;
                }
            );
           
        }
        
        


    }


    ]);