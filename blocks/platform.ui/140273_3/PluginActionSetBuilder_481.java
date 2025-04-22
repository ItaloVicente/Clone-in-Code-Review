			PluginAction action = ad.getAction();
			ActionContributionItem actionContribution = new PluginActionCoolBarContributionItem(action);
			actionContribution.setMode(ad.getMode());

			bars.addAdjunctContribution(actionContribution);

			IToolBarManager toolBarManager = bars.getToolBarManager(toolBarId);

			IContributionItem groupMarker = toolBarManager.find(toolGroupId);
			if (groupMarker == null) {
				toolBarManager.add(new Separator(toolGroupId));
			}
			IContributionItem refItem = findAlphabeticalOrder(toolGroupId, contributingId, toolBarManager);
			if (refItem != null && refItem.getId() != null) {
				toolBarManager.insertAfter(refItem.getId(), actionContribution);
			} else {
				toolBarManager.add(actionContribution);
			}
			toolBarManager.update(false);

		}

		protected void contributeCoolbarAction(ActionDescriptor ad, ActionSetActionBars bars) {
			String toolBarId = ad.getToolbarId();
			String toolGroupId = ad.getToolbarGroupId();
			if (toolBarId == null && toolGroupId == null) {
				return;
			}

			String contributingId = bars.getActionSetId();

			if (toolBarId == null || toolBarId.equals("")) { //$NON-NLS-1$
				toolBarId = contributingId;
			}

			if (!toolBarId.equals(contributingId)) {
				if (!isValidCoolItemId(toolBarId, window)) {
					toolBarId = contributingId;
				} else {
					adjunctActions.add(ad);
					return;
				}
			}

			PluginAction action = ad.getAction();
			ActionContributionItem actionContribution = new PluginActionCoolBarContributionItem(action);
			actionContribution.setMode(ad.getMode());

			IToolBarManager toolBar = bars.getToolBarManager(toolBarId);

			IContributionItem groupMarker = toolBar.find(toolGroupId);
			if (groupMarker == null) {
				toolBar.add(new Separator(toolGroupId));
			}
			toolBar.prependToGroup(toolGroupId, actionContribution);
			toolBar.update(false);

		}

		private boolean isValidCoolItemId(String id, WorkbenchWindow window) {
			ActionSetRegistry registry = WorkbenchPlugin.getDefault().getActionSetRegistry();
			if (registry.findActionSet(id) != null) {
