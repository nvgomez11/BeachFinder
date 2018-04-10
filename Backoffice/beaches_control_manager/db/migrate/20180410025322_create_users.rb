class CreateUsers < ActiveRecord::Migration[5.0]
  def change
    create_table :users do |t|
      t.string :name
      t.string :last_name
      t.string :nationality
      t.string :profile_picture
      t.string :phone_number
      t.string :email
      t.string :password
      t.text :location

      t.timestamps
    end
  end
end
