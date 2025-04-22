		parentControl.addControlListener(new ControlAdapter() {

			@Override
			public void controlResized(ControlEvent e) {
				parentRuler.relayout();
			}
		});
