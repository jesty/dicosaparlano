json.parliamentaries @results[:parliamentaries] do |parliamentary|
  json.extract! parliamentary, :id, :uri, :name, :surname, :birthday, :birthcountry, :electionday, :board, :gender, :description
  json.url parliamentary_url(parliamentary, format: :json)

  json.categories parliamentary.categories do |c|
     json.extract! c, :id, :name
     json.url category_url(c, format: :json)
  end
end

#json.categories @results[:categories] do |category|
#  json.extract! category, :id, :name, :parent
#  json.url category_url(category, format: :json)
#
#  json.parliamentaries category.parliamentaries do |parliamentary|
#    json.extract! parliamentary, :id, :uri, :name, :surname, :birthday, :birthcountry, :electionday, :board, :gender, :description
#    json.url parliamentary_url(parliamentary, format: :json)
#
#    json.categories parliamentary.categories do |c|
#      json.extract! category, :id, :name
#      json.url category_url(category, format: :json)
#    end
#  end
#end

#json.documents @results[:documents] do |document|
#  json.extract! document, :id, :uri, :date, :title, :body, :meta_title, :meta_desc, :meta_keywords
#  json.url document_url(document, format: :json)
#end
#json.acts @results[:acts] do |act|
#  json.extract! act, :id, :uri, :date, :title, :body
#  json.url act_url(act, format: :json)
#end
