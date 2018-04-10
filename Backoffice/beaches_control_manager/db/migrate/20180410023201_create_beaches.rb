class CreateBeaches < ActiveRecord::Migration[5.0]
  def change
    create_table :beaches do |t|
      t.string :name
      t.text :location
      t.string :sand_color
      t.text :description
      t.string :main_picture
      t.string :secondary_picture
      t.string :latitude
      t.string :longitude

      t.timestamps
    end
  end
end
