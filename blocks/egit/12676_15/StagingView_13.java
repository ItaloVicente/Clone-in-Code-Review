				final IStructuredSelection fileSelection = new StructuredSelection(
						stagingEntryList);

				if (!folderSelected) {
					Action openWorkingTreeVersion = new Action(
							UIText.CommitFileDiffViewer_OpenWorkingTreeVersionInEditorMenuLabel) {
						@Override
						public void run() {
							openSelectionInEditor(fileSelection);
						}
					};
					openWorkingTreeVersion.setEnabled(!submoduleSelected);
					menuMgr.add(openWorkingTreeVersion);
				}
