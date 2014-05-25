class Parliamentary < ActiveRecord::Base
  validates :uri, presence: true
  validates :uri, uniqueness: true

  has_and_belongs_to_many :acts
  has_and_belongs_to_many :documents
  has_and_belongs_to_many :categories

  def self.search(search)
    if search
      where('name LIKE ? OR surname LIKE ?', "%#{search}%", "%#{search}%")
    else
      scoped
    end
  end
end
