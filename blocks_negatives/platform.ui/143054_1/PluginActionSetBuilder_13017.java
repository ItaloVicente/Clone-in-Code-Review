            coolbarMgr.update(true);
        }

        private void revokeActionSetFromToolbar(IToolBarManager toolbarMgr,
                String actionsetId) {
            IContributionItem[] items = toolbarMgr.getItems();
            ArrayList itemsToRemove = new ArrayList();
            String id;
            for (IContributionItem item : items) {
                id = item.getId();
                if (id.equals(actionsetId)) {
                    itemsToRemove.add(item);
                    continue;
                }
                if (item instanceof PluginActionCoolBarContributionItem) {
                    id = ((PluginActionCoolBarContributionItem) item)
                            .getActionSetId();
                    if (actionsetId.equals(id)) {
