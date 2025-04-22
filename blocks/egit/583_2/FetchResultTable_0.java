		rejectedColor = new Color(parent.getDisplay(), 255, 0, 0);
		updatedColor = new Color(parent.getDisplay(), 0, 255, 0);
		upToDateColor = new Color(parent.getDisplay(), 245, 245, 245);

		tablePanel.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				rejectedColor.dispose();
				updatedColor.dispose();
				upToDateColor.dispose();
			}
		});
