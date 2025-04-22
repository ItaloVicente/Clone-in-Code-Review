		target.addValueChangeListener(new IValueChangeListener<Object>() {
			@Override
			public void handleValueChange(ValueChangeEvent<?> event) {
				log.add("target-set");
			}
		});
		model.addValueChangeListener(new IValueChangeListener<String>() {
			@Override
			public void handleValueChange(ValueChangeEvent<? extends String> event) {
				log.add("model-set");
			}
		});
