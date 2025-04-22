		model.value1.addValueChangeListener(new IValueChangeListener() {
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				System.out.println("Value 1: " + model.value1.getValue());
			}
		});
