		item.addControlListener(new ControlListener() {
			@Override
			public void controlMoved(ControlEvent e) {
				updateVisibleProgressItems(item);
			}

			@Override
			public void controlResized(ControlEvent e) {
				updateVisibleProgressItems(item);
			}
		});

