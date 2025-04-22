			dbc.bindValue(
					WidgetProperties.text().observe(combo),
					BeanProperties.value(ViewModel.class, "text").observe(viewModel));

			shell.pack();
			shell.open();
			return shell;
		}
	}

	private static IObservableFactory<Collection<String>, IObservableList<String>> getListDetailFactory() {
		return target -> {
			WritableList<String> list = WritableList.withElementType(String.class);
			list.addAll(target);
			return list;
		};
	}
