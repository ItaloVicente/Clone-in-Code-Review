			swtWidget.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					MUIElement element = (MUIElement) e.widget
							.getData(OWNING_ME);
					if (element != null)
						unbindWidget(element);
				}
