		source.addValueChangeListener(new IValueChangeListener<T>() {
			@Override
			public void handleValueChange(ValueChangeEvent<? extends T> event) {
				destination.setValue(event.diff.getNewValue());
			}
		});
