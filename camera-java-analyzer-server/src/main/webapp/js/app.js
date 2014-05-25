/*
 Applicazione in Angularjs
 */

'use strict';

var testUIApp = angular.module('testUIApp', ['ui.bootstrap']);


testUIApp.controller('searchController', function($scope, $http) {


    /* mappa della parole places, politicals e topics in un unica mappa chiave valore */
    $scope.searchInputItems = [];

    /* carico il parlamentari 
     * services/autocomplete/parlamentare 
     */
    //$http.get('data/politicals.json').
    $http.get('services/autocomplete/parlamentare').
            success(function(data, status, headers, config) {
                $scope.politicals = data;
                //console.log(data);
                for (var i in $scope.politicals) {
                    var el = {
                        "id": $scope.politicals[i].id, 
                        "name": $scope.politicals[i].nome + " " + $scope.politicals[i].cognome,
                        "type":"political",
                        "value":$scope.politicals[i]
                    };
                    $scope.searchInputItems.push(el);
                }
            }).
            error(function(data, status, headers, config) {
                console.log("Errore get politicals");
            });

    /* carico i luoghi 
     * services/autocomplete/luogo
     */
    //$http.get('data/places.json'). 
    $http.get('services/autocomplete/luogo').
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

    /* carico i topics 
     * services/autocomplete/categoria
     */
    //$http.get('data/topics.json').
    $http.get('services/autocomplete/categoria').
            success(function(data, status, headers, config) {
                $scope.topics = data;
                //console.log(data);
                for (var i in $scope.topics) {
                    var el = {
                        "id": $scope.topics[i].categoria,
                        "name": $scope.topics[i].categoria,
                        "type": "topic",
                        "value": $scope.topics[i]
                    };
                    $scope.searchInputItems.push(el);
                }
            }).
            error(function(data, status, headers, config) {
                console.log("Errore get topics");
            });


    /*  pannello di ricerca con i places, topics e politicians */
    $scope.searchedInputItems = [];     // elenco delle parole da ricercare
    $scope.searchInputItem = null;      // parola ricercata (place, topic, politician)
    $scope.isShowDetails = false;       // booleano per la visualizzazione dei dettagli
    $scope.isShowSearchResult = false;  // boolean per la visualizzazione dei risultati di riceca

    $scope.addSearchTopics = function() {
        //console.log($scope.searchInputItems);
        console.log("Aggiunto topic di ricerca: " + $scope.searchInputItem.name);
          
        /* se il searchInputItem e political allora faccio vedere subti la scheda */
        if ($scope.searchInputItem != null && $scope.searchInputItem.type == 'political') {            
            $scope.searchedInputItems = [];     
            $scope.searchedInputItems.push($scope.searchInputItem);     // aggiungo la ricerca all'elenco
            $scope.singlePoliticalDetails = $scope.searchInputItem.value;
            console.log($scope.singlePoliticalDetails);
            $scope.isShowDetails = true;                              
            $scope.isShowSearchResult = false;  
            $scope.searchInputItem = null;
            return;
        }
        
        $scope.isShowDetails = false;                               // nascondo i dettagli
        $scope.isShowSearchResult = true;                           // mostro la riceca
        $scope.searchedInputItems.push($scope.searchInputItem);     // aggiungo la ricerca all'elenco

        $scope.searchInputItem = null;
        
        var categorie_string="";
        for(var i in $scope.searchedInputItems){
            categorie_string += "categoria='"+$scope.searchedInputItems[i].name+"'&";
        }
        console.log(categorie_string);
        //$http.get('data/politicalsDetails.json').
                $http.get('services/search?').
                success(function(data, status, headers, config) {
                    for(var i in data){
                        var el = data[i];
                        var foto_id = el.id.replace('http://dati.camera.it/ocd/persona.rdf/p', '');
                        var thumbnail = 'http://documenti.camera.it/_dati/leg17/schededeputatiprototipo/foto/scheda_big/d'+foto_id+'.jpg';
                        el.thumbnail = thumbnail;
                    }
                    $scope.politicalsDetails = data;
                    console.log(data);
                }).
                error(function(data, status, headers, config) {
                    console.log("Errore nella ricerca dei topics");
                });
    };

    $scope.clearSearchedInputItems = function() {
        console.log("Clean all searchedInputItems: " + $scope.searchedInputItems);
        $scope.searchedInputItems = [];
        $scope.isShowDetails = false;
        $scope.isShowSearchResult = false;
    };

    /* carica i dettagli del parlamentare*/
    $scope.showDetails = function(id) {
        
        console.log(id);
        for (var i in $scope.politicals){
            if($scope.politicals[i] != null && id == $scope.politicals[i].id){
                console.log($scope.politicals[i]);
                $scope.singlePoliticalDetails = $scope.politicals[i];
                var foto_id = $scope.singlePoliticalDetails.id.replace('http://dati.camera.it/ocd/persona.rdf/p', '');
                var thumbnail = 'http://documenti.camera.it/_dati/leg17/schededeputatiprototipo/foto/scheda_big/d' + foto_id + '.jpg';
                $scope.singlePoliticalDetails.thumbnail = thumbnail;
                $scope.isShowDetails = true;
                $scope.isShowSearchResult = false;
                return;
            }
        }        

    }
});




