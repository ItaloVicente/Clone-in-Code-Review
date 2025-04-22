					if (monitor.isCanceled()) {
						page.setErrorMessage(NLS.bind(
								UIText.GenerateHistoryJob_CancelMessage, page
										.getName()));
						return Status.CANCEL_STATUS;
					}
					if (maxCommits > 0 && allCommits.size() > maxCommits)
						incomplete = true;
					if (incomplete || oldsz == allCommits.size())
