defmodule JSONLoader do
  alias Jason

  def load_data(menu_path, constraint_path) do
    {:ok, menu} = load_menu(menu_path)
    {:ok, constraints} = load_user_constraints(constraint_path)

    {:ok, menu, constraints}
  end

  def load_menu(file_path) do
    # Load file contents.
    {:ok, content} = File.read(file_path)

    # Parse the contents into Food struct.
    case Jason.decode(content) do
      {:ok, %{"foods" => foods}} ->
        {:ok, Enum.map(foods, &convert_food/1)}

      {:error, _} ->
        {:error, "Error parsing JSON"}
    end
  end

  def load_user_constraints(file_path) do
    # Load file contents.
    {:ok, content} = File.read(file_path)

    # Parse the contents into UserConstraints struct.
    case Jason.decode(content) do
      {:ok, json} ->
        {:ok, convert_user_constraints(json)}

      {:error, _} ->
        {:error, "Error parsing JSON"}
    end
  end

  defp convert_food(food) do
    %Food{
      name: food["name"],
      cost: food["cost"],
      satisfaction: food["satisfaction"],
      category: food["category"],
      ingredients: food["ingredients"],
      calories: food["calories"]
    }
  end

  defp convert_user_constraints(json) do
    %UserConstraints{
      budget: json["budget"],
      allergies: json["allergy"],
      calorie_limit: json["calorieLimit"]
    }
  end
end
