class CreateDocumentParliamentaryJoinTable < ActiveRecord::Migration
  def change
    create_join_table :documents, :parliamentaries do |t|
      # t.index [:document_id, :parliamentary_id]
      t.index [:parliamentary_id, :document_id], unique: true, :name => 'index_documents_parliamentaries'
    end
  end
end
