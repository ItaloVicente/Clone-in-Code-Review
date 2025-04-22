					@Override
					public void run() {
						openSelectionInEditor(tableViewer.getSelection());
					}
				};
				openWorkingTreeVersion.setEnabled(!submoduleSelected);
				menuMgr.add(openWorkingTreeVersion);
