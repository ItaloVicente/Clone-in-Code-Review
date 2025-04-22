                    if (toolBarMgr != null) {
                        if (bars instanceof ActionSetActionBars) {
                            contributeCoolbarAction(ad,
                                    (ActionSetActionBars) bars);
                        } else {
                            contributeToolbarAction(ad, toolBarMgr,
                                    toolAppendIfMissing);
                        }
                    }
                }
            }
        }

        /**
         * Contributes action from the action descriptor into the cool bar manager.
         */
        protected void contributeAdjunctCoolbarAction(ActionDescriptor ad,
                ActionSetActionBars bars) {
            String toolBarId = ad.getToolbarId();
            String toolGroupId = ad.getToolbarGroupId();

            String contributingId = bars.getActionSetId();
            ICoolBarManager coolBarMgr = bars.getCoolBarManager();
            if (coolBarMgr == null) {
                return;
            }

            PluginAction action = ad.getAction();
            ActionContributionItem actionContribution = new PluginActionCoolBarContributionItem(
                    action);
            actionContribution.setMode(ad.getMode());

            bars.addAdjunctContribution(actionContribution);

            IToolBarManager toolBarManager = bars.getToolBarManager(toolBarId);

            IContributionItem groupMarker = toolBarManager.find(toolGroupId);
            if (groupMarker == null) {
                toolBarManager.add(new Separator(toolGroupId));
            }
            IContributionItem refItem = findAlphabeticalOrder(toolGroupId,
                    contributingId, toolBarManager);
            if (refItem != null && refItem.getId() != null) {
                toolBarManager.insertAfter(refItem.getId(), actionContribution);
            } else {
                toolBarManager.add(actionContribution);
            }
            toolBarManager.update(false);

        }

        /**
         * Contributes action from the action descriptor into the cool bar manager.
         */
        protected void contributeCoolbarAction(ActionDescriptor ad,
                ActionSetActionBars bars) {
            String toolBarId = ad.getToolbarId();
            String toolGroupId = ad.getToolbarGroupId();
            if (toolBarId == null && toolGroupId == null) {
