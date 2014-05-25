class AddImageToParliamentaries < ActiveRecord::Migration
  def change
    add_column :parliamentaries, :image, :string
  end
end
