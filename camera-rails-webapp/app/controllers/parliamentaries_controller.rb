class ParliamentariesController < ApplicationController
  before_action :set_parliamentary, only: [:show, :edit, :update, :destroy]
  skip_before_filter :verify_authenticity_token

  # GET /parliamentaries
  # GET /parliamentaries.json
  def index
    @parliamentaries = Parliamentary.all
  end

  # GET /parliamentaries/1
  # GET /parliamentaries/1.json
  def show
    key = params['p']
    if(key.nil?)
        @documents = @parliamentary.documents
        @acts = @parliamentary.acts
    else
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
        @documents = @parliamentary.documents.joins(:categories).where(where_str).group("documents.id").having("count(documents.id) = #{i}")
        @acts = @parliamentary.acts.joins(:categories).where(where_str).group("acts.id").having("count(acts.id) = #{i}")
      else
        where_str += " categories.name='#{key}' "
        @documents = @parliamentary.documents.joins(:categories).where(where_str).group("documents.id")
        @acts = @parliamentary.acts.joins(:categories).where(where_str).group("acts.id")
      end
    end
  end

  # GET /parliamentaries/new
  def new
    @parliamentary = Parliamentary.new
  end

  # GET /parliamentaries/1/edit
  def edit
  end

  # POST /parliamentaries
  # POST /parliamentaries.json
  def create
    @parliamentary = Parliamentary.new(parliamentary_params)

    respond_to do |format|
      if @parliamentary.save
        format.html { redirect_to @parliamentary, notice: 'Parliamentary was successfully created.' }
        format.json { render action: 'show', status: :created, location: @parliamentary }
      else
        format.html { render action: 'new' }
        format.json { render json: @parliamentary.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /parliamentaries/1
  # PATCH/PUT /parliamentaries/1.json
  def update
    respond_to do |format|
      if @parliamentary.update(parliamentary_params)
        format.html { redirect_to @parliamentary, notice: 'Parliamentary was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @parliamentary.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /parliamentaries/1
  # DELETE /parliamentaries/1.json
  def destroy
    @parliamentary.destroy
    respond_to do |format|
      format.html { redirect_to parliamentaries_url }
      format.json { head :no_content }
    end
  end

  def import
    columns = [:uri, :name, :surname, :birthday, :birthcountry, :electionday, :board, :gender, :description]
    values = params[:_json]

    status = Parliamentary.import columns, values, :on_duplicate_key_update => columns

    respond_to do |format|
      format.json { head :no_content }
    end
  end

  def cimport
    values = params[:_json]

    values.each do |parliamentary|
      p = Parliamentary.where(:uri => parliamentary[:uri]).first

      if(p.nil?)
        p = Parliamentary.new
        p.uri = parliamentary[:uri]
      end
      
      p.name = parliamentary[:name]
      p.surname = parliamentary[:surname]
      p.birthday = parliamentary[:birthday]
      p.birthcountry = parliamentary[:birthcountry]
      p.electionday = parliamentary[:electionday]
      p.board = parliamentary[:board]
      p.gender = parliamentary[:gender]
      p.description = parliamentary[:description]
      
      p.save
    end

    respond_to do |format|
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_parliamentary
      @parliamentary = Parliamentary.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def parliamentary_params
      params.require(:parliamentary).permit(:uri, :name, :surname, :birthday, :birthcountry, :electionday, :board, :gender, :description)
    end
end
