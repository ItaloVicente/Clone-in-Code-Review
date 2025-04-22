		processFragments(fragmentList);
	}

	public void processFragments(Set<ModelFragmentWrapper> fragmentList) {
		for (ModelFragmentWrapper fragmentWrapper : fragmentList) {
			processFragment(fragmentWrapper.getFragmentContainer(), fragmentWrapper.getModelFragment(),
					fragmentWrapper.getContributorName(), fragmentWrapper.getContributorURI(),
					fragmentWrapper.isCheckExists());
		}
