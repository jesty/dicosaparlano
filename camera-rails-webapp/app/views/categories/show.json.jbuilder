json.extract! @category, :id, :name, :parent, :created_at, :updated_at

json.parliamentaries @category.parliamentaries do |parliamentary|
  json.extract! parliamentary, :id, :uri, :name, :surname, :gender
  json.url parliamentary_url(parliamentary, format: :json)
end

#  json.documents category.documents do |document|
#    json.extract! document, :id, :uri, :date, :title, :body, :meta_title, :meta_desc, :meta_keywords
#    json.url document_url(document, format: :json)
#  end
#
#  json.acts category.acts do |act|
#    json.extract! act, :id, :uri, :date, :title, :body
#    json.url act_url(act, format: :json)
#  end

