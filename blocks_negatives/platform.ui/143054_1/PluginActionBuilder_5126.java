            insertMenuGroup(menu, new GroupMarker(id));
        }

        /**
         * Contributes action from the action descriptor into the provided tool bar manager.
         */
        protected void contributeToolbarAction(ActionDescriptor ad,
                IToolBarManager toolbar, boolean appendIfMissing) {
            String tId = ad.getToolbarId();
            String tgroup = ad.getToolbarGroupId();
            if (tId == null && tgroup == null) {
