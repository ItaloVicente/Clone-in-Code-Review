				if (addUnstage)
					menuMgr.add(new Action(UIText.StagingView_UnstageItemMenuLabel) {
						@Override
						public void run() {
							unstage((IStructuredSelection) tableViewer.getSelection());
						}
					});
				boolean isNonResourceSelection = isNonResourceSelection(tableViewer.getSelection());
				if (addReplaceWithFileInGitIndex)
					if (isNonResourceSelection)
						menuMgr.add(new ReplaceAction(UIText.StagingView_replaceWithFileInGitIndex, selection, false));
					else
						menuMgr.add(createItem(ActionCommands.DISCARD_CHANGES_ACTION, tableViewer));	// replace with index
				if (addReplaceWithHeadRevision)
					if (isNonResourceSelection)
						menuMgr.add(new ReplaceAction(UIText.StagingView_replaceWithHeadRevision, selection, true));
					else
						menuMgr.add(createItem(ActionCommands.REPLACE_WITH_HEAD_ACTION, tableViewer));
				if (addLaunchMergeTool)
					menuMgr.add(createItem(ActionCommands.MERGE_TOOL_ACTION, tableViewer));
