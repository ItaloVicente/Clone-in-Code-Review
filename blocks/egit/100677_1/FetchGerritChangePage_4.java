							steps + 1);
					Change finalChange = completeChange(change,
							progress.newChild(1));
					if (finalChange == null) {
						Activator.showError(NLS.bind(
								UIText.FetchGerritChangePage_NoSuchChangeMessage,
								change.getChangeNumber()), null);
						return Status.CANCEL_STATUS;
					}
					final RefSpec spec = new RefSpec()
							.setSource(finalChange.getRefName())
							.setDestination(Constants.FETCH_HEAD);
					if (progress.isCanceled()) {
						return Status.CANCEL_STATUS;
					}
