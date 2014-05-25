class CreateParlamentaryCategoryJoinTable < ActiveRecord::Migration
  def change
    create_join_table :categories, :parliamentaries do |t|
      # t.index [:category_id, :parliamentary_id]
      t.index [:parliamentary_id, :category_id], unique: true, :name => 'index_categories_parliamentaries'
    end
  end
end
