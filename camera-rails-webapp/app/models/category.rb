class Category < ActiveRecord::Base
  validates :name, presence: true
  validates :name, uniqueness: true

  has_and_belongs_to_many :parliamentaries
  has_and_belongs_to_many :documents
  has_and_belongs_to_many :acts

  def self.search(search)
    if search
      where('name LIKE ?', "%#{search}%")
    else
      scoped
    end
  end
end
