class Document < ActiveRecord::Base
  validates :uri, presence: true
  validates :uri, uniqueness: true

  has_and_belongs_to_many :parliamentaries
  has_and_belongs_to_many :categories

  def self.search(search)
    if search
      where('title LIKE ? OR body LIKE ?', "%#{search}%", "%#{search}%")
    else
      scoped
    end
  end
end
