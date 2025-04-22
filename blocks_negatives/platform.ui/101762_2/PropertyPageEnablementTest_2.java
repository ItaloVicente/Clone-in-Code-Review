		contributors = PropertyPageContributorManager.getManager()
				.getApplicableContributors(testProject);
		for (Iterator iter = contributors.iterator(); iter.hasNext();) {
			RegistryPageContributor element = (RegistryPageContributor) iter
					.next();
			assertFalse("Matching project for AND", element.getPageId().equals(
					"org.eclipse.ui.tests.and"));
