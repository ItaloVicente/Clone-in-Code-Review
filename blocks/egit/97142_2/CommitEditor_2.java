			toolbar.add(createActionContributionItem(StashApplyHandler.ID,
					UIText.CommitEditor_toolbarApplyStash,
					UIIcons.STASH_APPLY));
			toolbar.add(createActionContributionItem(StashDropHandler.ID,
					UIText.CommitEditor_toolbarDeleteStash,
					PlatformUI.getWorkbench().getSharedImages()
							.getImageDescriptor(
									ISharedImages.IMG_TOOL_DELETE)));
