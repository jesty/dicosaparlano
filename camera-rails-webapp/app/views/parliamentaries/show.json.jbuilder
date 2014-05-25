json.extract! @parliamentary, :id, :uri, :name, :surname, :birthday, :birthcountry, :electionday, :board, :gender, :description, :created_at, :updated_at

json.categories @parliamentary.categories do |category|
  json.extract! category, :id, :name, :parent
  json.url category_url(category, format: :json)
end

json.documents @documents do |document|
  json.extract! document, :id, :uri, :title, :body, :image
  json.url document_url(document, format: :json)
end

json.acts @acts do |act|
  json.extract! act, :id, :uri, :title, :body, :image
  json.url act_url(act, format: :json)
end


