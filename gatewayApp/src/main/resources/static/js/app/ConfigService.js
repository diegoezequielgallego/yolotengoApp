angular.module('crudApp').factory('ConfigService',
    ['$http', '$q', 'urls',
        function ($http, $q, urls) {

            //Se crean todas las funciones dentro del objeto factory asi
            //a la hora de limpiarlo angular es mas optimo
            var factory = {

                getFacebookData: function () {
                    var deferred = $q.defer();
                    FB.api('/me', {
                       locale: 'en_US',
                       fields: 'id,first_name,last_name,email,link,gender,locale,picture'
                    }, function(response) {
                        if (!response || response.error) {
                            deferred.reject('Error occured');
                        } else {
                            deferred.resolve(response);
                        }
                    });
                    return deferred.promise;
                },
                loginFB: function () {
                    var deferred = $q.defer();
                    FB.login(}, function(response) {
                        if (!response || response.error) {
                            deferred.reject('Error occured');
                        } else {
                            deferred.resolve(response);
                        }
                    });
                    return deferred.promise;
                }


            };

            return factory;


        }
    ]);