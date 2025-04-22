				if (size == 1) {
					popupMgr.add(createPatchAction);
					createPatchAction.setEnabled(createPatchAction.isEnabled());
					popupMgr.add(createGitPatchAction);
					createGitPatchAction.setEnabled(createGitPatchAction.isEnabled());
				}
