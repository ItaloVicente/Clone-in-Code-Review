		target.addValueChangeListener(new IValueChangeListener() {
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				log.add("target-set");
			}
		});
		model.addValueChangeListener(new IValueChangeListener() {
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				log.add("model-set");
			}
		});
