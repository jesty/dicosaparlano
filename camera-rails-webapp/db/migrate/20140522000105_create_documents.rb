class CreateDocuments < ActiveRecord::Migration
  def change
    create_table :documents do |t|
      t.text :uri, unique: true
      t.datetime :date
      t.text :title
      t.text :body
      t.text :meta_title
      t.text :meta_desc
      t.text :meta_keywords

      t.timestamps
    end
  end
end
