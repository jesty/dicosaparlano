#!/usr/bin/ruby

require 'rubygems'
require 'sparql/client'
require 'open-uri'
require 'cgi'
require 'json'

urls = [
'http://news.centrodiascolto.it/video/tg3/2014-05-10/cronaca-giudiziaria-nera/inchiesta-su-exp-2015-nel-mirino-della-cosiddetta-cupo',
'http://news.centrodiascolto.it/video/tg3/2014-05-11/cronaca-giudiziaria-nera/il-caso-scajola-arrestata-nizza-chiara-rizzo-moglie-di',
'http://news.centrodiascolto.it/video/tg2/2014-05-10/religione/vaticano-papa-francesco-incontra-i-ragazzi-delle-scuole-300mila-piazz',
'http://news.centrodiascolto.it/video/tg3/2014-05-10/cronaca-giudiziaria-nera/inchiesta-su-exp-2015-nel-mirino-della-cosiddetta-cupo',
'http://news.centrodiascolto.it/video/tg2/2014-05-11/politica/matteo-renzi-ai-cantieri-navali-di-monfalcone',
'http://news.centrodiascolto.it/video/tg1/2014-05-12/politica/nigeria-boko-aram-mostra-un-video-le-ragazze-rapite-e-afferma-che-si-s',
'http://news.centrodiascolto.it/video/tg1/2014-05-12/cronaca-e-costume/ancora-una-strage-di-migranti-nel-mediterraneo-un-barcone-si-',
'http://news.centrodiascolto.it/video/tg2/2014-05-12/cronaca-e-costume/nuova-tragedia-dellimmigrazione-lampedusa-affonda-un-barcone-',
'http://news.centrodiascolto.it/video/tg2/2014-05-12/politica/ennesima-strage-di-immigrati-lampedusa-alfano-accusa-leuropa-ci-ha-las',
'http://news.centrodiascolto.it/video/tg2/2014-05-12/politica/exp-2015-renzi-fermare-i-delinquenti-non-lopera-grillo-attacca-fermiam',
'http://news.centrodiascolto.it/video/tg2/2014-05-12/politica/debiti-pubblica-amministrazione-tajani-inevitabile-apertura-procedura-',
'http://news.centrodiascolto.it/video/tg2/2014-05-12/politica/ucraina-lest-sceglie-la-secessione-ma-dopo-il-referendum-torna-alta-la',
'http://www.ilfattoquotidiano.it/2014/05/18/universita-il-lavoro-ce-per-medici-e-dentisti-e-per-gli-altri-paghe-da-fame/989621/',
'http://www.ilfattoquotidiano.it/2014/05/18/elezioni-berlusconi-contro-grillo-e-un-pazzo-lo-votano-i-disperati/989863/',
'http://www.ilfattoquotidiano.it/2014/05/18/referendum-svizzera-boccia-salario-minimo-di-oltre-3000-euro-mensili/989977/',
'http://tv.ilfattoquotidiano.it/2014/05/18/lultima-intervista-di-berlinguer-non-portiamo-in-europa-litalia-della-p2-e-della/280267/',
'http://www.ilfattoquotidiano.it/2014/05/17/elezioni-2014-il-duello-di-piazze-grillo-a-torino-e-renzi-in-emilia-romagna/989204/',
'http://www.ilfattoquotidiano.it/2014/05/18/reggio-emilia-scontro-sulla-piazza-blog-grillo-renzie-gnocco-fritto/989918/',
'http://www.ilfattoquotidiano.it/2014/05/18/padre-madre-e-figlio-12enne-uccisi-a-sprangate-in-gallura/989687/',
'http://www.ilfattoquotidiano.it/2014/05/18/veronica-lario-il-settimanale-chi-usato-come-unarma-miserabile-agguato/989816/',
]


remote_url = URI.parse("http://camera-newsly.rhcloud.com")
http = Net::HTTP.new(remote_url.host, remote_url.port)
request = Net::HTTP::Post.new("/services/analyze/bulk?test=false", initheader = {'Content-Type' =>'application/json'})
request.body = urls.to_json

response = http.request(request)
puts response
