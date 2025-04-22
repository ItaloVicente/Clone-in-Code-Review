		model.value2.addValueChangeListener(new IValueChangeListener<Object>() {
			@Override
			public void handleValueChange(ValueChangeEvent<?> event) {
				System.out.println("Value 2: " + model.value2.getValue());
			}
		});
