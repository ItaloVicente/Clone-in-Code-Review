            IConfigurationElement[] children = menuElement.getChildren();
            for (IConfigurationElement element : children) {
                String childName = element.getName();
                if (childName.equals(IWorkbenchRegistryConstants.TAG_SEPARATOR)) {
                    contributeSeparator(newMenu, element);
                } else if (childName.equals(IWorkbenchRegistryConstants.TAG_GROUP_MARKER)) {
                    contributeGroupMarker(newMenu, element);
                }
            }
        }

        /**
         * Contributes action from action descriptor into the provided menu manager.
         */
        protected void contributeMenuAction(ActionDescriptor ad,
                IMenuManager menu, boolean appendIfMissing) {
            String mpath = ad.getMenuPath();
            String mgroup = ad.getMenuGroup();
            if (mpath == null && mgroup == null) {
