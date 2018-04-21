Rails.application.routes.draw do

  resources :beaches
  resources :users

  get '/index', :to => static('index.html')

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
