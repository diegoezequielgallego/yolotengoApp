angular.module('crudApp').controller('ConfigController', ['ConfigService', '$scope',  
	function( service, $scope) {
		
		//se crean todas las variables y objetos dentro de 
		//otro asi a la hora de limpiarlos es mas optimo angular
		$scope.data = {
			registroMutantes: [],
			successMessage : '',
			errorMessage : '',
			countMutantDna: {},
			buttonRunDisable : false,
			dna : ''
		}

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
        
        
        $scope.getallmutants = function() {
            service.getallmutants().then(
                function (response) {
                	$scope.data.registroMutantes = response;
                	$scope.data.buttonRunDisable = false;
                },
                function (errResponse) {
                    console.error('Error while processing , Error :' + errResponse.data);
                    $scope.data.buttonRunDisable = false;
                }
            );
            
        }

        $scope.getStats = function() {
            $scope.data.buttonRunDisable = true;
            service.getStats().then(
                function (response) {
                    $scope.data.countMutantDna = response;
                    $scope.data.buttonRunDisable = false;
                },
                function (errResponse) {
                    console.error('Error while processing , Error :' + errResponse.data);
                    $scope.data.buttonRunDisable = false;
                }
            );

        }

    }


    ]);