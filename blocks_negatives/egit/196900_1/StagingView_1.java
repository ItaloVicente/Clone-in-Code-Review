					if (stagingEntryList.size() == 2
							&& stagingEntryList.get(0).isStaged()) {
						Action compareWithHead = new Action(
								UIText.StagingView_CompareWithEachOtherLabel,
								UIIcons.ELCL16_COMPARE_VIEW) {

							@Override
							public void run() {
								String left = stagingEntryList.get(0).getPath();
								String right = stagingEntryList.get(1)
										.getPath();
								CompareUtils.compareBetween(currentRepository,
										left, right, GitFileRevision.INDEX,
										GitFileRevision.INDEX, StagingView.this
												.getViewSite().getPage());
							}
						};
						menuMgr.add(compareWithHead);
