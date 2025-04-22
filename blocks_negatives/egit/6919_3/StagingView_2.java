					if (selectionIncludesNonWorkspaceResources) {
						menuMgr.add(new DeleteAction(selection));
					} else {
						DeleteResourceAction deleteResourceAction = new DeleteResourceAction(getSite());
						deleteResourceAction.selectionChanged(selection);
						ActionContributionItem item = new ActionContributionItem(deleteResourceAction);
						menuMgr.add(item);
					}
