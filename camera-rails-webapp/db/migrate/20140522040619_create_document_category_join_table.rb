class CreateDocumentCategoryJoinTable < ActiveRecord::Migration
  def change
    create_join_table :categories, :documents do |t|
      # t.index [:category_id, :document_id]
      t.index [:document_id, :category_id], unique: true, :name => 'index_categories_documents'
    end
  end
end
