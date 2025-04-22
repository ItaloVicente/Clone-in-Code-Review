				((Composite) parent).addDisposeListener(new DisposeListener() {
					@Override
					public void widgetDisposed(DisposeEvent e) {
						element.setWidget(null);
						element.setRenderer(null);
					}
