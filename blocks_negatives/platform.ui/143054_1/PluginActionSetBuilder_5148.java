            String contributingId = bars.getActionSetId();

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
            ActionContributionItem actionContribution = new PluginActionCoolBarContributionItem(
                    action);
            actionContribution.setMode(ad.getMode());

            IToolBarManager toolBar = bars.getToolBarManager(toolBarId);

            IContributionItem groupMarker = toolBar.find(toolGroupId);
            if (groupMarker == null) {
                toolBar.add(new Separator(toolGroupId));
            }
            toolBar.prependToGroup(toolGroupId, actionContribution);
            toolBar.update(false);

        }

        /**
         * Checks to see if the cool item id is in the given window.
         */
        private boolean isValidCoolItemId(String id, WorkbenchWindow window) {
            ActionSetRegistry registry = WorkbenchPlugin.getDefault()
                    .getActionSetRegistry();
            if (registry.findActionSet(id) != null) {
