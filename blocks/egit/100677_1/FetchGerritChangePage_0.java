					change = determineChangeFromString(refText.getText());
					if (change == null) {
						setErrorMessage(
								UIText.FetchGerritChangePage_MissingChangeMessage);
						return;
					}
