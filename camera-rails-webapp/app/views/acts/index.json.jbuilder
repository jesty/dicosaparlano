json.array!(@acts) do |act|
  json.extract! act, :id, :uri, :date, :title, :body, :image
  json.url act_url(act, format: :json)

  json.categories act.categories do |category|
    json.extract! category, :id, :name, :parent
    json.url category_url(category, format: :json)
  end

  json.parliamentaries act.parliamentaries do |parliamentary|
    json.extract! parliamentary, :id, :uri, :name, :surname, :birthday, :birthcountry, :electionday, :board, :gender, :description
    json.url parliamentary_url(parliamentary, format: :json)
  end
end
