				Action openWorkingTreeVersion = new Action(
						UIText.CommitFileDiffViewer_OpenWorkingTreeVersionInEditorMenuLabel) {
					@Override
					public void run() {
						openSelectionInEditor(tableViewer.getSelection());
					}
				};
				openWorkingTreeVersion.setEnabled(!submoduleSelected);
				menuMgr.add(openWorkingTreeVersion);
