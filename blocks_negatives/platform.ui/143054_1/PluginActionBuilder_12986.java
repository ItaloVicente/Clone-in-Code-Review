            insertMenuGroup(menu, new Separator(id));
        }

        /**
         * Creates a named menu group marker from the information in the configuration element.
         * If the marker already exists do not create a second.
         */
        protected void contributeGroupMarker(IMenuManager menu,
                IConfigurationElement element) {
            String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
            if (id == null || id.length() <= 0) {
