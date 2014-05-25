class CreateCategories < ActiveRecord::Migration
  def change
    create_table :categories do |t|
      t.text :name, unique: true
      t.integer :parent

      t.timestamps
    end
  end
end
