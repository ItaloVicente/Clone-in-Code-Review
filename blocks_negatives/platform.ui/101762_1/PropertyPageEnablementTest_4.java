		contributors = PropertyPageContributorManager.getManager()
				.getApplicableContributors(testFolder);
		for (Iterator iter = contributors.iterator(); iter.hasNext();) {
			RegistryPageContributor element = (RegistryPageContributor) iter
					.next();
			assertFalse("Matching folder for instanceof", element.getPageId()
					.equals("org.eclipse.ui.tests.instanceof"));
