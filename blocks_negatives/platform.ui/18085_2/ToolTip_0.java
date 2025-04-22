		this.control.addDisposeListener(new DisposeListener() {

			public void widgetDisposed(DisposeEvent e) {
				data = null;
				deactivate();
			}

		});

