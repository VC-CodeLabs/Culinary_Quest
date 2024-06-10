defmodule Codelab do
  use Application

  @impl true
  def start(_type, _args) do
    start()

    children = [
      # Starts a worker by calling: Foo.Worker.start_link(arg)
      # {Foo.Worker, arg}
    ]

    # See https://hexdocs.pm/elixir/Supervisor.html
    # for other strategies and supported options
    opts = [strategy: :one_for_one, name: Foo.Supervisor]
    Supervisor.start_link(children, opts)
  end

  def start do
    # IO.puts("Starting at: #{NaiveDateTime.local_now()}")
    app_dir = File.cwd!()

    # Update these values if you need to change the path to the files.
    menu_path = Path.join([app_dir, "priv/menu.json"])
    user_constraints_path = Path.join([app_dir, "priv/user_constraints.json"])

    case JSONLoader.load_data(menu_path, user_constraints_path) do
      {:ok, menu, user_constraints} ->
        # Group category.
        grouped_menu_items = Enum.group_by(menu, & &1.category)
        # IO.inspect(grouped_menu_items)

        result =
          MealPlanner.calculate_meal(
            [
              Map.get(grouped_menu_items, "Appetizer"),
              Map.get(grouped_menu_items, "Drink"),
              Map.get(grouped_menu_items, "Main Course"),
              Map.get(grouped_menu_items, "Dessert")
            ],
            user_constraints
          )

        # IO.puts("Finished at: #{NaiveDateTime.local_now()}")

        case result do
          {:ok, meal} when not is_nil(meal) -> IO.inspect(Jason.encode(meal))
          {:ok, nil} -> print_error()
          {:error, _} -> print_error()
        end
    end

    :ok
  end

  defp print_error do
    IO.inspect(
      Jason.encode(%{
        error:
          "Culinary conundrum! We couldn't whip up a meal that meets all your gastronomic guidelines."
      })
    )
  end
end
