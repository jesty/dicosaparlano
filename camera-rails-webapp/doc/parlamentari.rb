#!/usr/bin/ruby 
# -*- coding: utf-8 -*-

require 'rubygems'
require 'sparql/client'
require 'json'

sparql = SPARQL::Client.new("http://dati.camera.it/sparql/", {:method => :get})

# Parlamentare:
# - id = URI
# - Nome 
# - Cognome
# - Genere
# - Titolo studio (description)
# - Anagrafica
#   - LuogoNascita
#     - URI, Lat, Lon, Città, Regione, Provincia, Nazione
#   - DataNascita (Timestamp)
# - Data elezione e URI
# - Collegio

OCD = RDF::Vocabulary.new("http://dati.camera.it/ocd/")
DC = RDF::Vocabulary.new("http://purl.org/dc/elements/1.1/")

=begin
query = sparql.select().distinct(true).
  where([:persona, RDF::type, RDF::FOAF.Person]).
  where([:persona, RDF::FOAF.firstName, :nome]).
  where([:persona, RDF::FOAF.surname, :cognome]).
  where([:persona, DC.description, :description]).
  where([:persona, RDF::FOAF.gender, :gender]).
  where([:persona, RDF::URI('http://purl.org/vocab/bio/0.1/Birth'), :nascita]).
  where([:nascita, RDF::URI('http://purl.org/vocab/bio/0.1/date'), :dataNascita]).
  where([:nascita, RDF::RDFS.label, :nato]).
  where([:nascita, OCD::rif_luogo, :luogoNascitaUri]).
  where([:luogoNascitaUri, OCD::parentADM1, :comune]). 
  where([:luogoNascitaUri, OCD::parentADM2, :provincia]). 
  where([:luogoNascitaUri, OCD::parentADM3, :regione]).
  where([:luogoNascitaUri, RDF::GEO.lat, :lat]).
  where([:luogoNascitaUri, RDF::GEO.long, :lon]).
  where([:persona, OCD::rif_mandatoCamera, :mandato]).
  where([:d, RDF::type, OCD::deputato]).
  where([:d, OCD::rif_leg, RDF::URI('http://dati.camera.it/ocd/legislatura.rdf/repubblica_17')]).
  where([:d, OCD::rif_mandatoCamera, :mandato]).
  where([:mandato, OCD::rif_elezione, :elezione]).
  where([:elezione, DC::date, :elezioneData]).
  where([:elezione, DC::coverage, :collegio]).
  order(:cognome).limit(1)
=end

queryStr = "SELECT DISTINCT * WHERE { ?persona a <http://xmlns.com/foaf/0.1/Person> . ?persona <http://xmlns.com/foaf/0.1/firstName> ?nome . ?persona <http://xmlns.com/foaf/0.1/surname> ?cognome . ?persona <http://purl.org/dc/elements/1.1/description> ?description . ?persona <http://xmlns.com/foaf/0.1/gender> ?gender . OPTIONAL { ?persona <http://purl.org/vocab/bio/0.1/Birth> ?nascita . ?nascita <http://purl.org/vocab/bio/0.1/date> ?dataNascita . ?nascita <http://www.w3.org/2000/01/rdf-schema#label> ?nato . ?nascita <http://dati.camera.it/ocd/rif_luogo> ?luogoNascitaUri . ?luogoNascitaUri <http://dati.camera.it/ocd/parentADM1> ?comune . ?luogoNascitaUri <http://dati.camera.it/ocd/parentADM2> ?provincia . ?luogoNascitaUri <http://dati.camera.it/ocd/parentADM3> ?regione . ?luogoNascitaUri <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?lat . ?luogoNascitaUri <http://www.w3.org/2003/01/geo/wgs84_pos#long> ?lon } . OPTIONAL { ?persona <http://dati.camera.it/ocd/rif_mandatoCamera> ?mandato } . ?d a <http://dati.camera.it/ocd/deputato> . ?d <http://dati.camera.it/ocd/rif_leg> <http://dati.camera.it/ocd/legislatura.rdf/repubblica_17> . ?d <http://dati.camera.it/ocd/rif_mandatoCamera> ?mandato . ?mandato <http://dati.camera.it/ocd/rif_elezione> ?elezione . ?elezione <http://purl.org/dc/elements/1.1/date> ?elezioneData . ?elezione <http://purl.org/dc/elements/1.1/coverage> ?collegio . } ORDER BY ?cognome"

query = sparql.query(queryStr)

#.offset(10)
#.limit(10)

# puts query.solutions.count
#
# http://www.camera.it/_dati/leg04/deputati/foto/d17650.jpg
#
puts query.inspect.to_s
# puts query.solutions.inspect.to_s

# longest_key = query.solutions.keys.max { |a, b| a.length <=> b.length }
parlamentari = []
query.each_solution do |solution|
  #puts solution.inspect.to_s
  #printf "%-20s %s\n", solution.cognome.to_s, solution.nome.to_s
  #printf "%-20s\n", solution.nome.to_s
  #dataNascita =  DateTime.parse(solution.dataNascita.to_s)
  #puts dataNascita.to_s

  #puts "ID: #{solution.persona.to_s}"
  #puts "Cognome: #{solution.cognome.to_s}"
  #puts "Nome: #{solution.nome.to_s}"
  #puts "Genere: #{solution.gender.to_s}"
  #puts "Descrizione: #{solution.description.to_s}"
  #puts "Data Nascita: #{solution.dataNascita.to_s}"
  #puts "Luogo ID: #{solution.luogoNascitaUri.to_s}"
  #puts "  Lat: #{solution.lat.to_s}"
  #puts "  Lon: #{solution.lon.to_s}"
  #puts "  Comune: #{solution.comune.to_s}"
  #puts "  Provincia: #{solution.provincia.to_s}"
  #puts "  Regione: #{solution.regione.to_s}"
  #puts "Elezione ID: #{solution.elezione.to_s}"
  #puts "Elezione Data: #{solution.elezioneData.to_s}"
  #puts "Collegio: #{solution.collegio.to_s}"
  #puts

  dataNascita = nil
  if solution.respond_to? :dataNascita
    #dataNascita = DateTime.parse(solution.dataNascita.to_s).to_time.to_i.to_s
    dataNascita = solution.dataNascita.to_s
  end

  if solution.respond_to? :luogoNascitaUri
    luogoNascitaUri = solution.luogoNascitaUri.to_s
    lat = solution.lat.to_s if solution.respond_to? :lat
    lon = solution.lon.to_s if solution.respond_to? :lon
    comune = solution.comune.to_s if solution.respond_to? :comune
    provincia = solution.provincia.to_s if solution.respond_to? :provincia
    regione = solution.regione.to_s if solution.respond_to? :regione
    nazione = 'Italia' # solution.nazione.to_s,
  end

=begin
  persona = {
    :idPersona => solution.persona.to_s, 
    :cognome => solution.cognome.to_s,
    :nome => solution.nome.to_s,
    :genere => solution.gender.to_s,
    :descrizione => solution.description.to_s,
    :dataNascita => dataNascita,
    :luogoNascita => {
      :luogoId => luogoNascitaUri,
      :lat => lat,
      :lon => lon,
      :comune => comune,
      :provincia => provincia,
      :regione => regione,
      :nazione => nazione,
    },
    :elezione => {
      :elezioneId => solution.elezione.to_s,
      :elezioneData => DateTime.parse(solution.elezioneData.to_s).to_time.to_i.to_s,
      :collegio => solution.collegio.to_s,
    }
  }
=end
  #columns = [:uri, :name, :surname, :birthday, :birthcountry, :electionday, :board, :gender, :description]
  # luogoNascita = "#{solution.comune.to_s} #{solution.provincia.to_s} #{solution.regione.to_s}"
  luogoNascita = nil
  persona = {
    :uri => solution.persona.to_s, 
    :name => solution.nome.to_s, 
    :surname => solution.cognome.to_s, 
    :birthday => dataNascita.to_s, 
    :birthcountry => luogoNascita, 
    :electionday => solution.elezioneData.to_s, 
    :board => solution.collegio.to_s, 
    :gender => solution.gender.to_s, 
    :description => solution.description.to_s
  }
  parlamentari << persona
end

remote_url = URI.parse("http://camera-fej.rhcloud.com")
#remote_url = URI.parse("http://localhost:3000")
http = Net::HTTP.new(remote_url.host, remote_url.port)
request = Net::HTTP::Post.new("/parliamentaries/cimport.json", initheader = {'Content-Type' =>'application/json', 'Accept' => 'application/json'})

# puts JSON.generate(parlamentari)
# puts parlamentari.count

#request.set_form_data({:_json => JSON.generate(parlamentari) })
request.body = {:_json => parlamentari }.to_json

response = http.request(request)

puts response.inspect.to_s
