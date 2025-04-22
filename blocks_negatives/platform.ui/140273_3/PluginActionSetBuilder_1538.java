        }

        private void revokeActionSetFromMenu(IMenuManager menuMgr,
                String actionsetId) {
            IContributionItem[] items = menuMgr.getItems();
            ArrayList itemsToRemove = new ArrayList();
            String id;
            for (IContributionItem item : items) {
