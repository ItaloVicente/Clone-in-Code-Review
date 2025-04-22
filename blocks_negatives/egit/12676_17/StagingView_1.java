				if (element instanceof StagingEntry) {
					if (filterText != null && filterText.getText() != null
							&& filterText.getText().trim().length() > 0) {
						return ((StagingEntry) element)
								.getPath()
								.toUpperCase()
								.contains(
										filterText.getText().trim()
												.toUpperCase());
					}
				}
