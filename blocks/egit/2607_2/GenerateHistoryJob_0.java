					if (allCommits.size() == 0) {
						page.setErrorMessage(NLS.bind(
								UIText.GenerateHistoryJob_NoCommits,
								page.getName()));
						break;
					}
