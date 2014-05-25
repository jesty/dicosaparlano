#!/usr/bin/ruby 
# -*- coding: utf-8 -*-

require 'rubygems'
require 'sparql/client'
require 'open-uri'
require 'json'
require 'cgi'
require 'json'

sparql = SPARQL::Client.new("http://dati.camera.it/sparql/", {:method => :get})

OCD = RDF::Vocabulary.new("http://dati.camera.it/ocd/")
DC = RDF::Vocabulary.new("http://purl.org/dc/elements/1.1/")

atti = sparql.select().
  where([:atto, RDF::type, OCD::atto]).
  where([:atto, DC::title, :nomeAtto]).
  where([:legge, OCD::rif_leg, RDF::URI('http://dati.camera.it/ocd/legislatura.rdf/repubblica_17')]).
  where([:legge, OCD.lavoriPreparatori, :bnode]).
  where([:bnode , :lavoro, :atto ])

urls = []
atti.each_solution do |solution|

  dibattito = sparql.select().
    where([RDF::URI("#{solution.atto.to_s}"), OCD::rif_dibattito, :dibattito]).limit(2)

  discussione = sparql.select().
    where([RDF::URI("#{dibattito.solutions.first.dibattito.to_s}"), OCD::rif_discussione, :discussione]).limit(2)

  relation = sparql.select().
    where([RDF::URI("#{discussione.solutions.first.discussione.to_s}"), DC::relation, :relation]).limit(2)

  # FIX BUG change year from 16 to 17
  uri = URI.parse(relation.solutions.first.relation.to_s)
  params = CGI::parse(uri.query)
  params['idlegislatura'] = 17
  q = URI.encode_www_form(params)
  u = URI::HTTP.new("http", nil, uri.host, nil, nil, uri.path, nil, q, nil)

  urls << u
end
#puts urls.inspect.to_s

# call bulk
remote_url = URI.parse("http://camera-newsly.rhcloud.com")
http = Net::HTTP.new(remote_url.host, remote_url.port)
request = Net::HTTP::Post.new("/services/analyze/bulk?test=false", initheader = {'Content-Type' =>'application/json'})
request.body = urls.to_json

response = http.request(request)
puts response
