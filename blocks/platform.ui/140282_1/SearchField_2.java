				@Override
				protected void handleElementSelected(String string, Object selectedElement) {
					if (selectedElement instanceof QuickAccessElement) {
						QuickAccessElement element = (QuickAccessElement) selectedElement;
						addPreviousPick(string, element);
						txtQuickAccess.setText(""); //$NON-NLS-1$
						element.execute();

						if (txtQuickAccess.isDisposed()) {
							return;
						}
