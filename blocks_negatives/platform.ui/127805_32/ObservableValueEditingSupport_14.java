		target.addChangeListener(new IChangeListener() {
			@Override
			public void handleChange(ChangeEvent event) {
				dirty = true;
			}
		});
