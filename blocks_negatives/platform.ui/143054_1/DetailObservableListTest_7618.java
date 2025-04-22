		master.addValueChangeListener(new IValueChangeListener() {
			@Override
			public void handleValueChange(ValueChangeEvent event) {
				detailObservable[0].dispose();
			}
		});
