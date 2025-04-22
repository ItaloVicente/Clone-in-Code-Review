				@Override
				protected void handleElementSelected(String text, Object selectedElement) {
					lastSelectionFilter = text;
					if (selectedElement instanceof QuickAccessElement) {
						addPreviousPick(text, (QuickAccessElement) selectedElement);
						storeDialog(getDialogSettings());

						final QuickAccessElement element = (QuickAccessElement) selectedElement;
						window.getShell().getDisplay().asyncExec(() -> element.execute());
