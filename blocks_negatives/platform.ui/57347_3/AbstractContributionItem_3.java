					toolItem.addDisposeListener(new DisposeListener() {
						@Override
						public void widgetDisposed(DisposeEvent e) {
							if (menu != null && !menu.isDisposed()) {
								creator.dispose();
								mmenu.setWidget(null);
							}
