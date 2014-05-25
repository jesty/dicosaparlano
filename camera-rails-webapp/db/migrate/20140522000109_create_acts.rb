class CreateActs < ActiveRecord::Migration
  def change
    create_table :acts do |t|
      t.text :uri, unique: true
      t.datetime :date
      t.text :title
      t.text :body

      t.timestamps
    end
  end
end
