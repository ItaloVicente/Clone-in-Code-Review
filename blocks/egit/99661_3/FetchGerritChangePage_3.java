				Change input = determineChangeFromString(
						clipText.trim());
				if (input != null) {
					String toInsert = input.getChangeNumber().toString();
					if (input.patchSetNumber != null) {
						toInsert = toInsert + '/' + input.getPatchSetNumber();
						if (text.getText().trim().isEmpty() || text
								.getSelectionText().equals(text.getText())) {
							String subdir = Integer.toString(
									input.getChangeNumber().intValue() % 100);
							toInsert = "refs/changes/" + subdir + '/' //$NON-NLS-1$
									+ toInsert;
						}
					}
