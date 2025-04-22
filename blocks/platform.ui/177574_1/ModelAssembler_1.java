		if (fragmentContributions != null) {
			for (IModelFragmentContribution fc : fragmentContributions) {
				if (initial || !ApplyAttribute.INITIAL.equals(fc.getApplyAttribute())) {
					Bundle bundle = FrameworkUtil.getBundle(fc.getClass());
					MModelFragments fragmentsContainer = getFragmentsContainer(fc.getUri(), bundle.getSymbolicName());
					if (fragmentsContainer == null) {
						continue;
					}
					for (MModelFragment fragment : fragmentsContainer.getFragments()) {
						boolean checkExist = !initial
								&& ApplyAttribute.NOTEXISTS.equals(fc.getApplyAttribute());
						wrappers.add(
								new ModelFragmentWrapper(fragmentsContainer, fragment, bundle.getSymbolicName(),
										URIHelper.constructPlatformURI(bundle), checkExist)); // $NON-NLS-1$
					}
				}
			}
		}

