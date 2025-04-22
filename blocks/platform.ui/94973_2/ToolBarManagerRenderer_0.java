						getUpdater().updateContributionItems(e -> {
							if (e instanceof MToolBarElement) {
								if (((MUIElement) ((MToolBarElement) e).getParent()) == toolbarModel) {
									return true;
								}
							}
							return false;
						});
