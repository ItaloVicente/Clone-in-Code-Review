					menuMgr.add(new Action(UIText.StagingView_MergeTool,
							UIIcons.MERGE_TOOL) {
						@Override
						public void run() {
							CommonUtils.runCommand(ActionCommands.MERGE_TOOL_ACTION, fileSelection);
						}
					});
