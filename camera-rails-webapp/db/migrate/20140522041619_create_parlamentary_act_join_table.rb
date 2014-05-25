class CreateParlamentaryActJoinTable < ActiveRecord::Migration
  def change
    create_join_table :acts, :parliamentaries do |t|
      # t.index [:act_id, :parliamentary_id]
      t.index [:parliamentary_id, :act_id], unique: true
    end
  end
end
