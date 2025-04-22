					if (change.getPatchSetNumber() != null) {
						if (!list.getResult().contains(change)) {
							setErrorMessage(
									UIText.FetchGerritChangePage_UnknownChangeRefMessage);
							return;
						}
					} else {
						Change fromGerrit = findHighestPatchSet(
								list.getResult(),
								change.getChangeNumber().intValue());
						if (fromGerrit == null) {
							setErrorMessage(NLS.bind(
									UIText.FetchGerritChangePage_NoSuchChangeMessage,
									change.getChangeNumber()));
							return;
