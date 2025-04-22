        }

        /**
         * Set the visibility test.
         *
         * @param element the element
         */
        public void setVisibilityTest(IConfigurationElement element) {
            visibilityTest = new ActionExpression(element);
        }

        @Override
		public void contribute(IMenuManager menu, boolean menuAppendIfMissing,
                IToolBarManager toolbar, boolean toolAppendIfMissing) {
            boolean visible = true;

            if (visibilityTest != null) {
                ISelection selection = selProvider.getSelection();
                if (selection instanceof IStructuredSelection) {
                    visible = visibilityTest
                            .isEnabledFor((IStructuredSelection) selection);
                } else {
                    visible = visibilityTest.isEnabledFor(selection);
                }
            }

            if (visible) {
				super.contribute(menu, menuAppendIfMissing, toolbar,
                        toolAppendIfMissing);
