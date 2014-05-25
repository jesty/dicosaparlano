class ActsController < ApplicationController
  before_action :set_act, only: [:show, :edit, :update, :destroy]
  skip_before_filter :verify_authenticity_token

  # GET /acts
  # GET /acts.json
  def index
    @acts = Act.all
  end

  # GET /acts/1
  # GET /acts/1.json
  def show
  end

  # GET /acts/new
  def new
    @act = Act.new
  end

  # GET /acts/1/edit
  def edit
  end

  # POST /acts
  # POST /acts.json
  def create
    @act = Act.new(act_params)

    respond_to do |format|
      if @act.save
        format.html { redirect_to @act, notice: 'Act was successfully created.' }
        format.json { render action: 'show', status: :created, location: @act }
      else
        format.html { render action: 'new' }
        format.json { render json: @act.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /acts/1
  # PATCH/PUT /acts/1.json
  def update
    respond_to do |format|
      if @act.update(act_params)
        format.html { redirect_to @act, notice: 'Act was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @act.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /acts/1
  # DELETE /acts/1.json
  def destroy
    @act.destroy
    respond_to do |format|
      format.html { redirect_to acts_url }
      format.json { head :no_content }
    end
  end

  def import
    columns = [:uri, :date, :title, :body]
    values = params[:_json]

    status = Act.import columns, values, :on_duplicate_key_update => columns

    respond_to do |format|
      format.json { head :no_content }
    end
  end

  def cimport
    values = params[:_json]

    values.each do |act|
      d = Act.where(:uri => act[:uri]).first

      if(d.nil?)
        d = Act.new
      end

      d.title = act[:title]
      d.body = act[:description]
      d.image = act[:image]
      d.uri = act[:uri]
      d.date = act[:date]
      d.save
     
      act[:categories].each do |category|
        c = Category.where(:name => category[:categoria]).first
        
        if(c.nil?)
          c = Category.new
          c.name = category[:categoria]
          c.save
        end
        
        unless d.categories.exists?(c)
          d.categories << c
        end
      end 

      act[:parlamentari].each do |parliamentary|
        p = Parliamentary.where(:uri => parliamentary[:id]).first
        if(p.nil?)
          p = Parliamentary.new
          p.uri = parliamentary[:id]
          p.name = parliamentary[:nome]
          p.surname = parliamentary[:cognome]
        end

        d.categories.each do |cat|
          unless p.categories.exists?(cat)
            p.categories << cat
          end
        end
        p.save

        unless d.parliamentaries.exists?(p)
          d.parliamentaries << p
        end
      end
      d.save
    end

    respond_to do |format|
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_act
      @act = Act.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def act_params
      params.require(:act).permit(:uri, :date, :title, :body)
    end
end
