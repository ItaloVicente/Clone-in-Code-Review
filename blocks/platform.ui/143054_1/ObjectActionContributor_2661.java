			filterTest.addFilterElement(element);
		}

		public void setVisibilityTest(IConfigurationElement element) {
			visibilityTest = new ActionExpression(element);
		}

		public void setEnablementTest(IConfigurationElement element) {
			try {
				enablement = ExpressionConverter.getDefault().perform(element);
			} catch (CoreException e) {
				WorkbenchPlugin.log(e);
			}
		}

		public boolean isApplicableTo(Object object) {
			boolean result = true;
			if (visibilityTest != null) {
				result = result && visibilityTest.isEnabledFor(object);
				if (!result) {
