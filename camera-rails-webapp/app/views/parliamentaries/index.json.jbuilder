json.array!(@parliamentaries) do |parliamentary|
  json.extract! parliamentary, :id, :uri, :name, :surname, :birthday, :birthcountry, :electionday, :board, :gender, :description
  json.url parliamentary_url(parliamentary, format: :json)
  
  json.categories parliamentary.categories do |category|
    json.extract! category, :id, :name, :parent
    json.url category_url(category, format: :json)
  end
end
