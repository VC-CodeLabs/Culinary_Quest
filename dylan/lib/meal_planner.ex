defmodule MealPlanner do
  def calculate_meal(grouped_menu_items, user_constraints) do
    # Calculate all possible meal combinations.
    meal_combinations = generate_combinations(grouped_menu_items)
    # IO.puts("There are #{length(meal_combinations)} possible meal combinations")

    # IO.puts("Calculating results ... #{NaiveDateTime.local_now()}")
    results =
      Enum.map(meal_combinations, fn combination ->
        perform_calculation(combination, user_constraints)
      end)

    # IO.puts("Got all results, starting filtering and sorting | #{NaiveDateTime.local_now()}")

    # Filter out any invalid meals, take just the value (remove the :valid atom), and then sort by totalSatisfaction first, then cost, then calories.
    valid_meals =
      results
      |> Enum.filter(fn {status, _value} -> status == :valid end)
      |> Enum.map(fn {:valid, value} -> value end)
      |> Enum.sort_by(&{-&1.total_satisfaction, &1.total_cost, &1.calories})

    # IO.puts("There were #{Enum.count(valid_meals)} valid meals | #{NaiveDateTime.local_now()}")
    # Enum.each(valid_meals, &IO.inspect/1)

    {:ok, Enum.at(valid_meals, 0)}
  end

  defp generate_combinations(categories) do
    Enum.reduce(categories, [[]], fn category, acc ->
      for item <- category, combination <- acc do
        [item | combination]
      end
    end)
  end

  defp perform_calculation(combination, user_constraints) do
    totals =
      combination
      |> Enum.reduce(%{cost: 0, satisfaction: 0, calories: 0, ingredients: []}, fn food, acc ->
        %{
          cost: acc.cost + food.cost,
          satisfaction: acc.satisfaction + food.satisfaction,
          calories: acc.calories + food.calories,
          ingredients: food.ingredients ++ acc.ingredients
        }
      end)

    if totals.cost <= user_constraints.budget and
         totals.calories <= user_constraints.calorie_limit and
         !contains_any?(totals.ingredients, user_constraints.allergies) do
      {:valid,
       %{
         selected_foods:
           combination
           |> Enum.map(fn e ->
             %{
               category: e.category,
               name: e.name
             }
           end),
         total_cost: totals.cost,
         total_satisfaction: totals.satisfaction,
         calories: totals.calories
       }}
    else
      {:invalid, "Not a fit"}
    end
  end

  def contains_any?(list1, list2) do
    Enum.any?(list1, fn x -> Enum.member?(list2, x) end)
  end
end
