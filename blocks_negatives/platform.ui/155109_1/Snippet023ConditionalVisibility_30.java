		new ControlUpdater(oneOfTwo) {
			@Override
			protected void updateControl() {
				if (rangeSelected.getValue()) {
					stackLayout.topControl = rangeGroup;
					oneOfTwo.layout();
				} else if (textSelected.getValue()) {
					stackLayout.topControl = textGroup;
					oneOfTwo.layout();
				}
