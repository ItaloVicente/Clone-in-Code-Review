					try {
						if (change.getPatchSetNumber() != null) {
							if (!list.get().contains(change)) {
								setErrorMessage(
										UIText.FetchGerritChangePage_UnknownChangeRefMessage);
								return;
							}
						} else {
							Change fromGerrit = findHighestPatchSet(list.get(),
									change.getChangeNumber().intValue());
							if (fromGerrit == null) {
								setErrorMessage(NLS.bind(
										UIText.FetchGerritChangePage_NoSuchChangeMessage,
										change.getChangeNumber()));
								return;
							}
