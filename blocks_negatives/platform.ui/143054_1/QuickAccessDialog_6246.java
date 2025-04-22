						@Override
						protected QuickAccessElement getPerfectMatch(String filter) {
							QuickAccessElement perfectMatch = (QuickAccessElement) elementMap
									.get(filter);
							return perfectMatch;
						}

						@Override
						protected void handleElementSelected(String text, Object selectedElement) {
							if (selectedElement instanceof QuickAccessElement) {
								addPreviousPick(text, selectedElement);
								storeDialog(getDialogSettings());
