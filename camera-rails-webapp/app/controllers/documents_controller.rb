class DocumentsController < ApplicationController
  before_action :set_document, only: [:show, :edit, :update, :destroy]
  skip_before_filter :verify_authenticity_token

  # GET /documents
  # GET /documents.json
  def index
    @documents = Document.all
  end

  # GET /documents/1
  # GET /documents/1.json
  def show
  end

  # GET /documents/new
  def new
    @document = Document.new
  end

  # GET /documents/1/edit
  def edit
  end

  # POST /documents
  # POST /documents.json
  def create
    @document = Document.new(document_params)

    respond_to do |format|
      if @document.save
        format.html { redirect_to @document, notice: 'Document was successfully created.' }
        format.json { render action: 'show', status: :created, location: @document }
      else
        format.html { render action: 'new' }
        format.json { render json: @document.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /documents/1
  # PATCH/PUT /documents/1.json
  def update
    respond_to do |format|
      if @document.update(document_params)
        format.html { redirect_to @document, notice: 'Document was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @document.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /documents/1
  # DELETE /documents/1.json
  def destroy
    @document.destroy
    respond_to do |format|
      format.html { redirect_to documents_url }
      format.json { head :no_content }
    end
  end

  def import
    columns = [:uri, :date, :title, :body, :meta_title, :meta_desc, :meta_keywords]
    values = params[:_json]

    status = Document.import columns, values, :on_duplicate_key_update => columns

    # Added category for document
    # Added parliamentary for document
    respond_to do |format|
      format.json { head :no_content }
    end
  end

  def cimport
    values = params[:_json]

    if values.nil? or values.class == String
      respond_to do |format|
        format.json { render json: params, status: :unprocessable_entity }
      end
    else

    values.each do |document|
      d = Document.where(:uri => document[:uri]).first

      if(d.nil?)
        d = Document.new
      end

      d.title = document[:title]
      d.body = document[:description]
      d.image = document[:image]
      d.uri = document[:uri]
      d.date = document[:date]
      d.save
     
      document[:categories].each do |category|
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

      document[:parlamentari].each do |parliamentary|
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
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_document
      @document = Document.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def document_params
      params.require(:document).permit(:uri, :date, :title, :body, :meta_title, :meta_desc, :meta_keywords)
    end
end
