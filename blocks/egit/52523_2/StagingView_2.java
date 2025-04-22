
					Action openCompareWithIndex = new Action(
							UIText.StagingView_CompareWithIndexMenuLabel) {
						public void run() {
							runCommand(ActionCommands.COMPARE_WITH_INDEX_ACTION,
									fileSelection);
						};
					};
					menuMgr.add(openCompareWithIndex);
