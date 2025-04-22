            return window.getWindowConfigurer();
        }

        /**
         * Returns whether the given id is for a cool item.
         *
         * @param the item id
         * @return <code>true</code> if it is a cool item,
         * and <code>false</code> otherwise
         */
        /* package */boolean containsCoolItem(String id) {
            ICoolBarManager cbManager = getCoolBarManager();
            if (cbManager == null) {
