					suggestion = NLS.bind(
							UIText.FetchGerritChangePage_SuggestedRefNamePattern,
							change.getChangeNumber(),
							change.getPatchSetNumber());
				}
				if (!branchTextEdited) {
					branchText.setText(suggestion);
				}
				if (!tagTextEdited) {
					tagText.setText(suggestion);
