json.extract! user, :id, :name, :last_name, :nationality, :profile_picture, :phone_number, :email, :password, :location, :created_at, :updated_at
json.url user_url(user, format: :json)
