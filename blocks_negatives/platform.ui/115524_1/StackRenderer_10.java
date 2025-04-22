		tabFolder.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				updateMRUValue(tabFolder);
			}
		});
