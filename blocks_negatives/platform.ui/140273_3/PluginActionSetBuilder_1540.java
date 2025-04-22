                }
			}
            Iterator iter = itemsToRemove.iterator();
            while (iter.hasNext()) {
                IContributionItem item = (IContributionItem) iter.next();
                menuMgr.remove(item);
            }
            menuMgr.update(true);
        }

        private void revokeActionSetFromCoolbar(ICoolBarManager coolbarMgr,
                String actionsetId) {
            IContributionItem[] items = coolbarMgr.getItems();
            ArrayList itemsToRemove = new ArrayList();
            String id;
            for (IContributionItem item : items) {
                id = item.getId();
                if (actionsetId.equals(id)) {
                    itemsToRemove.add(item);
                    continue;
                }
                if (item instanceof IToolBarManager) {
                    revokeActionSetFromToolbar((IToolBarManager) item,
                            actionsetId);
                } else if (item instanceof IToolBarContributionItem) {
                    id = ((IToolBarContributionItem) item).getId();
                    if (actionsetId.equals(id)) {
