/*
 Applicazione in Angularjs
 */

'use strict';

var testUIApp = angular.module('testUIApp', ['ui.bootstrap']);


testUIApp.controller('searchController', function($scope, $http) {


    /* mappa della parole places, politicals e topics in un unica mappa chiave valore */
    $scope.searchInputItems = [];
    $scope.loadingMSG = "";

    /* carico il parlamentari 
     * http://camera-fej.rhcloud.com/parliamentaries.json
     * data/parliamentaries.json
     */
    //$http.get('data/parliamentaries.json').
    $http.get('http://camera-fej.rhcloud.com/parliamentaries.json').
        success(function(data, status, headers, config) {
            $scope.politicals = data;
            //console.log(data);
            for (var i in $scope.politicals) {
                var el = {
                    "id": $scope.politicals[i].id, 
                    "name": $scope.politicals[i].name + " " + $scope.politicals[i].surname,
                    "type":"political",
                    "value":$scope.politicals[i]
                };
                $scope.searchInputItems.push(el);
            }
            $scope.loadingMSG +=" (Caricati " + $scope.politicals.length + " parlamentari) ";
        }).
        error(function(data, status, headers, config) {
            console.log("Errore get parliamentaries.json"+data);
            $scope.loadingMSG += " Errore di carcamento dei parlamentari";
        });

    /* carico i luoghi 
     * http://camera-newsly.rhcloud.com/services/autocomplete/luogo
     */
    /*
    //$http.get('data/places.json'). 
    $http.get('http://camera-newsly.rhcloud.com/services/autocomplete/luogo').
            success(function(data, status, headers, config) {
                $scope.places = data;
                //console.log(data);
                for (var i in $scope.places) {
                    var el = {
                        "id": $scope.places[i], 
                        "name": $scope.places[i],
                        "type": "place",
                        "value": $scope.places[i]
                    };
                    $scope.searchInputItems.push(el);
                }
            }).
            error(function(data, status, headers, config) {
                console.log("Errore get places");
            });
    */
   
    /* carico i topics 
     * http://camera-fej.rhcloud.com/categories.json
     * data/categories.json
     */
    //$http.get('data/categories.json').
    $http.get('http://camera-fej.rhcloud.com/categories.json').
        success(function(data, status, headers, config) {
            $scope.topics = data;
            for (var i in $scope.topics) {
                var el = {
                    "id": $scope.topics[i].id,
                    "name": $scope.topics[i].name,
                    "type": "topic",
                    "value": $scope.topics[i]
                };
                $scope.searchInputItems.push(el);
            }
            $scope.loadingMSG += " (Caricati " + $scope.topics.length + " temi) ";
        }).
        error(function(data, status, headers, config) {
            console.log("Errore get topics "+data);
            $scope.loadingMSG += " Errore di carcamento dei temi";
        });


    /*  pannello di ricerca con i places, topics e politicians */
    $scope.searchedInputItems = [];     // elenco delle parole da ricercare
    $scope.searchInputItem = null;      // parola ricercata (place, topic, politician)
    $scope.isShowDetails = false;       // booleano per la visualizzazione dei dettagli
    $scope.isShowSearchResult = false;  // boolean per la visualizzazione dei risultati di riceca

    $scope.searchStatusMSG="";
    $scope.addSearchTopics = function() {
        
        $scope.searchStatusMSG = "Ricerca in corso...";
        if($scope.searchInputItem == null){
            return;
        }
        console.log("Aggiunto topic di ricerca: " + $scope.searchInputItem.name);
          
        /* se il searchInputItem e political allora faccio vedere subti la scheda */
        if ($scope.searchInputItem != null && $scope.searchInputItem.type == 'political') {            
            $scope.searchedInputItems = [];     
 
            /*
             * http://camera-fej.rhcloud.com/parliamentaries/1.json
             * carico i dati del politico
             */
            $http.get($scope.searchInputItem.value.url).
                success(function(data, status, headers, config) {

                    $scope.singlePoliticalDetails = data;
                    var foto_id = $scope.singlePoliticalDetails.uri.replace('http://dati.camera.it/ocd/persona.rdf/p', '');
                    var thumbnail = 'http://documenti.camera.it/_dati/leg17/schededeputatiprototipo/foto/scheda_big/d' + foto_id + '.jpg';
                    $scope.singlePoliticalDetails.thumbnail = thumbnail;
                    $scope.searchStatusMSG = "Ricerca in completata";
                    /* controlli image per atti e documenti*/
                    for (var i in $scope.singlePoliticalDetails.documents) {
                        if ($scope.singlePoliticalDetails.documents[i].image == null || $scope.singlePoliticalDetails.documents[i].image=='') {
                            $scope.singlePoliticalDetails.documents[i].image = "./img/empty.jpeg";
                        }
                    }
                    for (var i in $scope.singlePoliticalDetails.acts) {
                        if ($scope.singlePoliticalDetails.acts[i].image == null || $scope.singlePoliticalDetails.acts[i].image == '') {
                            $scope.singlePoliticalDetails.acts[i].image = "./img/empty.jpeg";
                        }
                    }
                    console.log($scope.singlePoliticalDetails);
                }).
                error(function(data, status, headers, config) {
                    console.log("Errore nella ricerca dei singlePoliticalDetails"+$scope.searchInputItem.value.url);
                    $scope.searchStatusMSG = "Ricerca in completata";
                });
            
            $scope.isShowDetails = true;                              
            $scope.isShowSearchResult = false;  
            $scope.searchInputItem = null;
            return;
        }
        
        $scope.isShowDetails = false;                               // nascondo i dettagli
        $scope.isShowSearchResult = true;                           // mostro la riceca
        $scope.searchedInputItems.push($scope.searchInputItem);     // aggiungo la ricerca all'elenco

        $scope.searchInputItem = null;
        
        var categorie_query_string="http://camera-fej.rhcloud.com/search.json?";
        for(var i in $scope.searchedInputItems){
            categorie_query_string += "p[]="+$scope.searchedInputItems[i].name+"&";
        }
        console.log(categorie_query_string);
        /*
         * http://camera-fej.rhcloud.com/search.json?p[]=sport&p[]=economia
         * newdata/parlamentaries.json
         */
        //$http.get('data/search2.json').
        //$http.get('data/politicalsDetails.json').
        $http.get(categorie_query_string).
            success(function(data, status, headers, config) {
                for(var i in data.parliamentaries){
                    var el = data.parliamentaries[i];
                    var foto_id = el.uri.replace('http://dati.camera.it/ocd/persona.rdf/p', '');
                    var thumbnail = 'http://documenti.camera.it/_dati/leg17/schededeputatiprototipo/foto/scheda_big/d'+foto_id+'.jpg';
                    el.thumbnail = thumbnail;
                }
                $scope.politicalsDetails = data.parliamentaries;
                $scope.searchStatusMSG = "Ricerca in completata";
            }).
            error(function(data, status, headers, config) {
                console.log("Errore nella ricerca delle categorie " + categorie_query_string);
                $scope.searchStatusMSG = "Ricerca in completata";
            });
    };

    $scope.clearSearchedInputItems = function() {
        console.log("Clean all searchedInputItems: " + $scope.searchedInputItems);
        $scope.searchedInputItems = [];
        $scope.isShowDetails = false;
        $scope.isShowSearchResult = false;
    };

    /* carica i dettagli del parlamentare */
    $scope.showDetails = function(id) {
        console.log(id);
        
        var categorie_query_string = id+"?";
        for (var i in $scope.searchedInputItems) {
            categorie_query_string += "p[]=" + $scope.searchedInputItems[i].name + "&";
        }
        console.log(categorie_query_string);
        /*
         * http://camera-fej.rhcloud.com/parliamentaries/29.json?p[]=sport&p[]=economia
         * data/parlamentaries29.json
         */
        //$http.get('data/parlamentaries29.json').
        //$http.get('data/politicalsDetails.json').
        $http.get(categorie_query_string).
            success(function(data, status, headers, config) {

                var foto_id = data.uri.replace('http://dati.camera.it/ocd/persona.rdf/p', '');
                var thumbnail = 'http://documenti.camera.it/_dati/leg17/schededeputatiprototipo/foto/scheda_big/d' + foto_id + '.jpg';
                data.thumbnail = thumbnail;

                $scope.singlePoliticalDetails = data;
                
                /* controlli image per atti e documenti*/
                for (var i in $scope.singlePoliticalDetails.documents) {
                    if ($scope.singlePoliticalDetails.documents[i].image == null || $scope.singlePoliticalDetails.documents[i].image == '') {
                        $scope.singlePoliticalDetails.documents[i].image = "./img/empty.jpeg";
                    }
                }
                for (var i in $scope.singlePoliticalDetails.acts) {
                    if ($scope.singlePoliticalDetails.acts[i].image == null || $scope.singlePoliticalDetails.acts[i].image == '') {
                        $scope.singlePoliticalDetails.acts[i].image = "./img/empty.jpeg";
                    }
                }
                
                console.log(data);
            }).
            error(function(data, status, headers, config) {
                console.log("Errore nella ricerca dei singlePoliticalDetails");
            });


        $scope.isShowDetails = true;
        $scope.isShowSearchResult = false;
        return;      

    }
});




