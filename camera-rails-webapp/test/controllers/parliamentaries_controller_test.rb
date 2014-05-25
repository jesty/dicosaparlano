require 'test_helper'

class ParliamentariesControllerTest < ActionController::TestCase
  setup do
    @parliamentary = parliamentaries(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:parliamentaries)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create parliamentary" do
    assert_difference('Parliamentary.count') do
      post :create, parliamentary: { birthcountry: @parliamentary.birthcountry, birthday: @parliamentary.birthday, board: @parliamentary.board, description: @parliamentary.description, electionday: @parliamentary.electionday, gender: @parliamentary.gender, name: @parliamentary.name, surname: @parliamentary.surname, uri: @parliamentary.uri }
    end

    assert_redirected_to parliamentary_path(assigns(:parliamentary))
  end

  test "should show parliamentary" do
    get :show, id: @parliamentary
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @parliamentary
    assert_response :success
  end

  test "should update parliamentary" do
    patch :update, id: @parliamentary, parliamentary: { birthcountry: @parliamentary.birthcountry, birthday: @parliamentary.birthday, board: @parliamentary.board, description: @parliamentary.description, electionday: @parliamentary.electionday, gender: @parliamentary.gender, name: @parliamentary.name, surname: @parliamentary.surname, uri: @parliamentary.uri }
    assert_redirected_to parliamentary_path(assigns(:parliamentary))
  end

  test "should destroy parliamentary" do
    assert_difference('Parliamentary.count', -1) do
      delete :destroy, id: @parliamentary
    end

    assert_redirected_to parliamentaries_path
  end
end
