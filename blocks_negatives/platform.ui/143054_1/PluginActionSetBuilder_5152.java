            ICoolBarManager coolBarMgr = bars.getCoolBarManager();
            PluginAction action = ad.getAction();
            PluginActionCoolBarContributionItem actionContribution = new PluginActionCoolBarContributionItem(
                    action);
            actionContribution.setMode(ad.getMode());

            bars.removeAdjunctContribution(actionContribution);

            IContributionItem cbItem = coolBarMgr.find(toolBarId);
            if (cbItem != null) {
