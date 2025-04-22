				item.addDisposeListener(new DisposeListener() {

					@Override
					public void widgetDisposed(DisposeEvent e) {
						page.dispose();
					}
				});
