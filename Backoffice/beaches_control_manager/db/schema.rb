# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20180412075407) do

  create_table "beaches", force: :cascade do |t|
    t.string   "beach_name"
    t.string   "location"
    t.string   "sand_color"
    t.text     "description"
    t.string   "main_image"
    t.string   "secondary_image"
    t.string   "latitude"
    t.string   "longitude"
    t.string   "wave_type"
    t.boolean  "snorkeling"
    t.boolean  "swimming"
    t.boolean  "shade"
    t.boolean  "night_life"
    t.boolean  "camping_zone"
    t.boolean  "protected_area"
    t.boolean  "cristal_water"
    t.string   "vegetation"
    t.datetime "created_at",      null: false
    t.datetime "updated_at",      null: false
  end

  create_table "users", force: :cascade do |t|
    t.string   "name"
    t.string   "last_name"
    t.string   "nationality"
    t.string   "profile_picture"
    t.string   "phone_number"
    t.string   "email"
    t.string   "password"
    t.text     "location"
    t.datetime "created_at",      null: false
    t.datetime "updated_at",      null: false
  end

end
