
		ctf.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				updateMruValue(ctf);
			}
		});
