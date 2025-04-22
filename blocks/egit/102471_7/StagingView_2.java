				if (addAssumeUnchanged) {
					menuMgr.add(
							new Action(UIText.StagingView_Assume_Unchanged,
									UIIcons.ASSUME_UNCHANGED) {
								@Override
								public void run() {
									assumeUnchanged(selection);
								}
							});
				}
				if (addUntrack) {
					menuMgr.add(new Action(UIText.StagingView_Untrack,
							UIIcons.UNTRACK) {
						@Override
						public void run() {
							untrack(selection);
						}
					});
				}
