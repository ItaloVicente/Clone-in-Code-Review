				new DisposeListener() {
					@Override
					public void widgetDisposed(DisposeEvent e) {
						cancelAnimation();
					}
				});
