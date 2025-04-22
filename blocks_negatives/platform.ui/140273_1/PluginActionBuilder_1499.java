            actions.add(desc);
        }

        /**
         * Contributes submenus and/or actions into the provided menu and tool bar
         * managers.
         *
         * The elements added are filtered based on activity enablement.
         * @param menu the menu to contribute to
         * @param menuAppendIfMissing whether to append missing groups to menus
         * @param toolbar the toolbar to contribute to
         * @param toolAppendIfMissing whether to append missing groups to toolbars
         */
        public void contribute(IMenuManager menu, boolean menuAppendIfMissing,
                IToolBarManager toolbar, boolean toolAppendIfMissing) {
            if (menus != null && menu != null) {
                for (int i = 0; i < menus.size(); i++) {
					IConfigurationElement menuElement = (IConfigurationElement) menus
                            .get(i);
                    contributeMenu(menuElement, menu, menuAppendIfMissing);
                }
            }

            if (actions != null) {
                for (int i = 0; i < actions.size(); i++) {
