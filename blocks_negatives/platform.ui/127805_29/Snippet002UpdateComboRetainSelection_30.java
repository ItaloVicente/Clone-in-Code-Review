			dbc.bindValue(WidgetProperties.text().observe(combo), BeanProperties.value(viewModel.getClass(), "text")
					.observe(viewModel));

            shell.pack();
            shell.open();
            return shell;
        }
    }

    private static IObservableFactory getListDetailFactory() {
        return new IObservableFactory() {
            @Override
			public IObservable createObservable(Object target) {
                WritableList list = WritableList.withElementType(String.class);
                list.addAll((Collection) target);
                return list;
            }
        };
    }
