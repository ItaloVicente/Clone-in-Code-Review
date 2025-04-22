            /**
             * Search for the element whose extension has ID equal to that of
             * the field desiredBrowserSupport.
             *
             * @param elements
             *            the elements to search
             * @return the element or <code>null</code>
             */
            private IConfigurationElement findDesiredElement(IConfigurationElement [] elements) {
                for (IConfigurationElement element : elements) {
                    if (desiredBrowserSupportId.equals(element.getDeclaringExtension().getUniqueIdentifier())) {
