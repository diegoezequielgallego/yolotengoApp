angular.module('crudApp').factory('ConfigService',
    ['$http', '$q', 'urls',
        function ($http, $q, urls) {

            var factory = {
                getFacebookData: function () {
                    var deferred = $q.defer();
                    FB.api('/me', {
                       fields: 'id,first_name,last_name,email,link,gender,picture'
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
                    FB.login(function(response) {
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