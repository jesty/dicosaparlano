# encoding: UTF-8
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

ActiveRecord::Schema.define(version: 20140524132952) do

  create_table "acts", force: true do |t|
    t.text     "uri"
    t.datetime "date"
    t.text     "title"
    t.text     "body"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "image"
  end

  create_table "acts_categories", id: false, force: true do |t|
    t.integer "act_id",      null: false
    t.integer "category_id", null: false
  end

  add_index "acts_categories", ["category_id", "act_id"], name: "index_acts_categories_on_category_id_and_act_id", unique: true, using: :btree

  create_table "acts_parliamentaries", id: false, force: true do |t|
    t.integer "act_id",           null: false
    t.integer "parliamentary_id", null: false
  end

  add_index "acts_parliamentaries", ["parliamentary_id", "act_id"], name: "index_acts_parliamentaries_on_parliamentary_id_and_act_id", unique: true, using: :btree

  create_table "categories", force: true do |t|
    t.text     "name"
    t.integer  "parent"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "categories_documents", id: false, force: true do |t|
    t.integer "category_id", null: false
    t.integer "document_id", null: false
  end

  add_index "categories_documents", ["document_id", "category_id"], name: "index_categories_documents", unique: true, using: :btree

  create_table "categories_parliamentaries", id: false, force: true do |t|
    t.integer "category_id",      null: false
    t.integer "parliamentary_id", null: false
  end

  add_index "categories_parliamentaries", ["parliamentary_id", "category_id"], name: "index_categories_parliamentaries", unique: true, using: :btree

  create_table "documents", force: true do |t|
    t.text     "uri"
    t.datetime "date"
    t.text     "title"
    t.text     "body"
    t.text     "meta_title"
    t.text     "meta_desc"
    t.text     "meta_keywords"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "image"
  end

  create_table "documents_parliamentaries", id: false, force: true do |t|
    t.integer "document_id",      null: false
    t.integer "parliamentary_id", null: false
  end

  add_index "documents_parliamentaries", ["parliamentary_id", "document_id"], name: "index_documents_parliamentaries", unique: true, using: :btree

  create_table "parliamentaries", force: true do |t|
    t.text     "uri"
    t.string   "name"
    t.string   "surname"
    t.datetime "birthday"
    t.text     "birthcountry"
    t.datetime "electionday"
    t.string   "board"
    t.string   "gender"
    t.string   "description"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "image"
  end

end
