class AddCommentsToBeaches < ActiveRecord::Migration[5.0]
  def change
    add_column :beaches, :comments, :string
  end
end
