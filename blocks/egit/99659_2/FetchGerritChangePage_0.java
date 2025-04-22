				ChangeList list = changeRefs.get(uriCombo.getText());
				if (list != null && list.isDone()
						&& !list.getResult().contains(change)) {
					setErrorMessage(
							UIText.FetchGerritChangePage_UnknownChangeRefMessage);
					return;
				}
