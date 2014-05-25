class AddImageToDocuments < ActiveRecord::Migration
  def change
    add_column :documents, :image, :string
  end
end
