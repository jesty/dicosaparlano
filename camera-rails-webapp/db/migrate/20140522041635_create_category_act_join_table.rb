class CreateCategoryActJoinTable < ActiveRecord::Migration
  def change
    create_join_table :acts, :categories do |t|
      # t.index [:act_id, :category_id]
      t.index [:category_id, :act_id], unique: true
    end
  end
end
