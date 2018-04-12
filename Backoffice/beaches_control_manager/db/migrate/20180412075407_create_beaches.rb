class CreateBeaches < ActiveRecord::Migration[5.0]
  def change
    create_table :beaches do |t|
      t.string :beach_name
      t.string :location
      t.string :sand_color
      t.text :description
      t.string :main_image
      t.string :secondary_image
      t.string :latitude
      t.string :longitude
      t.string :wave_type
      t.boolean :snorkeling
      t.boolean :swimming
      t.boolean :shade
      t.boolean :night_life
      t.boolean :camping_zone
      t.boolean :protected_area
      t.boolean :cristal_water
      t.string :vegetation

      t.timestamps
    end
  end
end
