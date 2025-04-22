				Change input = determineChangeFromString(
						clipText.trim());
				if (input != null) {
					String toInsert = input.getChangeNumber().toString();
					if (input.getPatchSetNumber() != null) {
						if (text.getText().trim().isEmpty() || text
								.getSelectionText().equals(text.getText())) {
							toInsert = input.getRefName();
						} else {
							toInsert = toInsert + '/'
									+ input.getPatchSetNumber();
						}
					}
