json.array!(@documents) do |document|
  json.extract! document, :id, :uri, :date, :title, :body, :image, :meta_title, :meta_desc, :meta_keywords
  json.url document_url(document, format: :json)

  json.categories document.categories do |category|
    json.extract! category, :id, :name, :parent
    json.url category_url(category, format: :json)
  end

  json.parliamentaries document.parliamentaries do |parliamentary|
    json.extract! parliamentary, :id, :uri, :name, :surname, :birthday, :birthcountry, :electionday, :board, :gender, :description
    json.url parliamentary_url(parliamentary, format: :json)
  end
end
