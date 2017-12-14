angular.module('crudApp').factory('ConfigService',
    ['$http', '$q', 'urls',
        function ($http, $q, urls) {

            //Se crean todas las funciones dentro del objeto factory asi
            //a la hora de limpiarlo angular es mas optimo
            var factory = {

                getStats: function () {
                    var deferred = $q.defer();
                    $http.get(urls.USER_SERVICE_API + "stats")
                        .then(
                        function (response) {
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            deferred.reject(errResponse);
                        }
                    );
                    return deferred.promise;
                },

                getallmutants: function () {
                    var deferred = $q.defer();
                    $http.get(urls.USER_SERVICE_API + "getallmutants/")
                        .then(
                        function (response) {
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            deferred.reject(errResponse);
                        }
                    );
                    return deferred.promise;
                },

                isMutant: function (dna) {
                    var deferred = $q.defer();
                    $http.post(urls.USER_SERVICE_API + "mutant/", dna)
                        .then(
                        function (response) {
                            //loadAllUsers();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            //console.error('Error while creating User : '+errResponse.data.errorMessage);
                            deferred.reject(errResponse);
                        }
                    );
                    return deferred.promise;
                }

            };

            return factory;


        }
    ]);