		contributors = PropertyPageContributorManager.getManager()
				.getApplicableContributors(testProject);
		for (Iterator iter = contributors.iterator(); iter.hasNext();) {
			RegistryPageContributor element = (RegistryPageContributor) iter
					.next();
			assertFalse("Matching project for OR", element.getPageId().equals(
					"org.eclipse.ui.tests.or"));

