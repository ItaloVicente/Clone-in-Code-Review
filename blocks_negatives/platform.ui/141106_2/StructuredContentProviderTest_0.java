		mathFunction = new SomeMathFunction(inputSet);
		currentFunction.addValueChangeListener(new IValueChangeListener() {
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				mathFunction
						.setOperation(((Integer) currentFunction.getValue())
								.intValue());
			}
		});
		mathFunction.setOperation(((Integer) currentFunction.getValue())
				.intValue());
