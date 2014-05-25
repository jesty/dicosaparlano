class AddImageToActs < ActiveRecord::Migration
  def change
    add_column :acts, :image, :string
  end
end
