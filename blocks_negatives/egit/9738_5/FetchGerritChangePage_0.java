				Change change = Change.fromRef(refText.getText());
				if (change != null) {
					branchText.setText(NLS
							.bind(UIText.FetchGerritChangePage_SuggestedRefNamePattern,
									change.getChangeNumber(),
									change.getPatchSetNumber()));
					tagText.setText(branchText.getText());
				} else {
				}
				checkPage();
