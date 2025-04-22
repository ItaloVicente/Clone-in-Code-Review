            filterTest.addFilterElement(element);
        }

        /**
         * Set the visibility test.
         *
         * @param element the element
         */
        public void setVisibilityTest(IConfigurationElement element) {
            visibilityTest = new ActionExpression(element);
        }

        /**
         * Set the enablement test.
         *
         * @param element the element
         */
        public void setEnablementTest(IConfigurationElement element) {
            try {
                enablement = ExpressionConverter.getDefault().perform(element);
            } catch (CoreException e) {
                WorkbenchPlugin.log(e);
            }
        }

        /**
         * Returns true if name filter is not specified for the contribution
         * or the current selection matches the filter.
         *
         * @param object the object to test
         * @return whether we're applicable
         */
        public boolean isApplicableTo(Object object) {
            boolean result = true;
            if (visibilityTest != null) {
                result = result && visibilityTest.isEnabledFor(object);
                if (!result) {
