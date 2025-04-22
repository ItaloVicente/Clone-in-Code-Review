					if (stagingEntryList.size() == 1
							&& stagingEntryList.get(0).isStaged()) {
						Action compareWithHead = new Action(
								UIText.StagingView_CompareWithHeadMenuLabel,
								UIIcons.ELCL16_COMPARE_VIEW) {

							@Override
							public void run() {
								runCommand(
										ActionCommands.COMPARE_INDEX_WITH_HEAD_ACTION,
										fileSelection);
							}
						};
						menuMgr.add(compareWithHead);
					}
