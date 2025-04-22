				ChangeAndPatchSet input = determineChangeFromString(
						clipText.trim());
				if (input != null) {
					String toInsert = input.changeNumber;
					if (input.patchSetNumber != null) {
						toInsert = toInsert + '/' + input.patchSetNumber;
						if (text.getText().trim().isEmpty() || text
								.getSelectionText().equals(text.getText())) {
							String subdir = Integer.toString(
									Integer.parseInt(input.changeNumber) % 100);
							toInsert = "refs/changes/" + subdir + '/' //$NON-NLS-1$
									+ toInsert;
						}
					}
