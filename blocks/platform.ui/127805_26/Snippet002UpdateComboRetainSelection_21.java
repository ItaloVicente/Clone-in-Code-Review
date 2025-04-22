					List<String> newList = new ArrayList<>();
					newList.add("Chocolate");
					newList.add("Vanilla");
					newList.add("Mango Parfait");
					newList.add("beef");
					newList.add("Cheesecake");
					newList.add(Integer.toString(++count));
					viewModel.setChoices(newList);
				}
			});

			System.out.println(viewModel.getText());

			DataBindingContext dbc = new DataBindingContext();

			@SuppressWarnings("unchecked")
			IObservableList<String> list = MasterDetailObservables.detailList(
					BeanProperties.value(ViewModel.class, "choices",
							(Class<List<String>>) (Object) List.class).observe(viewModel),
					getListDetailFactory(),
					String.class);
