class SearchController < ApplicationController
  # GET /search
  # GET /search.json
  # POST /search
  # POST /search.json
  def index
    key = params['p']
    categories = parliamentaries = documents = acts = nil

    if(key.nil?)
      categories = Category.all
    else
      # categories = Category.search(key)
      # parliamentaries = Parliamentary.search(key)
      # documents = Document.search(key)
      # acts = Act.search(key)
      where_str = ''
      if(key.class == Array)
        i = 0
        key.each do |k|
          if(i > 0)
            where_str += " OR "
          end
          where_str += " categories.name='#{k}' "
          i += 1
        end
        parliamentaries = Parliamentary.joins(:categories).where(where_str).group("parliamentaries.id").having("count(parliamentaries.id) = #{i}")
      else
        where_str += " categories.name='#{key}' "
        parliamentaries = Parliamentary.joins(:categories).where(where_str).group("parliamentaries.id")
      end
    end
    @results = {:parliamentaries => parliamentaries, :categories => categories, :documents => documents, :acts => acts}
  end

  # POST /search/category
  # POST /search/category.json
  def category
    key = params['p']
    @categories = Category.search(key)
  end

  # POST /search/parliamentary
  # POST /search/parliamentary.json
  def parliamentary
    key = params['p']
    @parliamentaries = Parliamentary.search(key)
  end

  # POST /search/document
  # POST /search/document.json
  def document
    key = params['p']
    @documents = Document.search(key)
  end

  # POST /search/act
  # POST /search/act.json
  def act
    key = params['p']
    @acts = Act.search(key)
  end
end
