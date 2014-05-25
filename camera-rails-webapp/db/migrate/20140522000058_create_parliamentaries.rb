class CreateParliamentaries < ActiveRecord::Migration
  def change
    create_table :parliamentaries do |t|
      t.text :uri, unique: true
      t.string :name
      t.string :surname
      t.datetime :birthday
      t.text :birthcountry
      t.datetime :electionday
      t.string :board
      t.string :gender
      t.string :description

      t.timestamps
    end
  end
end
