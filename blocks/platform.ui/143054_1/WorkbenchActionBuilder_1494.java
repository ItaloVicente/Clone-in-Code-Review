				buildProjectAction.setEnabled(enabled);
				toggleAutoBuildAction.setChecked(workspace.isAutoBuilding());
				cleanAction.setEnabled(BuildUtilities.isEnabled(projects, IncrementalProjectBuilder.CLEAN_BUILD));

				ICoolBarManager coolBarManager = getActionBarConfigurer()
						.getCoolBarManager();
				IContributionItem cbItem = coolBarManager
						.find(IWorkbenchActionConstants.TOOLBAR_FILE);
				if (!(cbItem instanceof IToolBarContributionItem)) {
					IDEWorkbenchPlugin.log("File toolbar contribution item is missing"); //$NON-NLS-1$
					return;
				}
				IToolBarContributionItem toolBarItem = (IToolBarContributionItem) cbItem;
				IToolBarManager toolBarManager = toolBarItem.getToolBarManager();
				if (toolBarManager == null) {
					IDEWorkbenchPlugin.log("File toolbar is missing"); //$NON-NLS-1$
					return;
				}
				boolean found = toolBarManager.find(buildAllAction.getId()) != null;
				if (enabled && !found) {
					toolBarManager.appendToGroup(IWorkbenchActionConstants.BUILD_GROUP,
							buildAllAction);
					toolBarManager.update(false);
					toolBarItem.update(ICoolBarManager.SIZE);
				} else if (buildAllAction != null && found && !enabled) {
					toolBarManager.remove(buildAllAction.getId());
					toolBarManager.update(false);
					toolBarItem.update(ICoolBarManager.SIZE);
				}
			}
