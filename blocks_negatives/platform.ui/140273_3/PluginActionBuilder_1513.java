            IContributionItem sep = null;
            sep = toolbar.find(tgroup);
            if (sep == null) {
                if (appendIfMissing) {
                    addGroup(toolbar, tgroup);
                } else {
                    WorkbenchPlugin
                    return;
                }
            }
            try {
                insertAfter(toolbar, tgroup, ad);
            } catch (IllegalArgumentException e) {
                WorkbenchPlugin
            }
        }

        /**
         * Inserts the separator or group marker into the menu. Subclasses may override.
         */
        protected void insertMenuGroup(IMenuManager menu,
                AbstractGroupMarker marker) {
            menu.add(marker);
        }

        /**
         * Inserts an action after another named contribution item.
         * Subclasses may override.
         */
        protected void insertAfter(IContributionManager mgr, String refId,
                ActionDescriptor desc) {
            final PluginActionContributionItem item = new PluginActionContributionItem(desc.getAction());
            item.setMode(desc.getMode());
