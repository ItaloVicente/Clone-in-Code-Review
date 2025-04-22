                    WorkbenchPlugin
                    return;
                }
            }

            try {
                insertAfter(parent, mgroup, ad);
            } catch (IllegalArgumentException e) {
                WorkbenchPlugin
            }
        }

        /**
         * Creates a named menu separator from the information in the configuration element.
         * If the separator already exists do not create a second.
         */
        protected void contributeSeparator(IMenuManager menu,
                IConfigurationElement element) {
            String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
            if (id == null || id.length() <= 0) {
