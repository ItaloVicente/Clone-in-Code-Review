	private ModelFragmentWrapper getModelFragmentWrapperFromBundle(Bundle bundle) {
		String fragmentHeader = bundle.getHeaders("").get(MODEL_FRAGMENT_HEADER); //$NON-NLS-1$
		String[] fr = fragmentHeader.split(";"); //$NON-NLS-1$
		if (fr.length > 0) {
			String uri = fr[0];

			MModelFragments fragmentsContainer = getFragmentsContainer(uri, bundle.getSymbolicName());
			if (fragmentsContainer != null) {
				for (MModelFragment fragment : fragmentsContainer.getFragments()) {
					return new ModelFragmentWrapper(fragmentsContainer, fragment, bundle.getSymbolicName(),
							URIHelper.constructPlatformURI(bundle), false); // $NON-NLS-1$
				}
			}
		} else {
			logger.error("Model-Fragment header value \"{0}\" in bundle \\\"{1}\\\" is invalid", //$NON-NLS-1$
					fragmentHeader, bundle.getSymbolicName());
		}

		return null;
	}

	private ModelFragmentWrapper getModelFragmentWrapperFromBundle(Bundle bundle, boolean initial) {
		String fragmentHeader = bundle.getHeaders("").get(MODEL_FRAGMENT_HEADER); //$NON-NLS-1$
		String[] fr = fragmentHeader.split(";"); //$NON-NLS-1$
		if (fr.length > 0) {
			String uri = fr[0];
			String apply = fr.length > 1 ? fr[1].split("=")[1] : "always"; //$NON-NLS-1$ //$NON-NLS-2$

			if (!ALWAYS.equals(apply) && !INITIAL.equals(apply) && !NOTEXISTS.equals(apply)) {
				logger.error("Model-Fragment header apply attribute \"{0}\" is invalid, falling back to always", //$NON-NLS-1$
						apply);
				apply = ALWAYS;
			}

			if (initial || !INITIAL.equals(apply)) {
				MModelFragments fragmentsContainer = getFragmentsContainer(uri, bundle.getSymbolicName());
				if (fragmentsContainer != null) {
					for (MModelFragment fragment : fragmentsContainer.getFragments()) {
						boolean checkExist = !initial && NOTEXISTS.equals(apply);
						return new ModelFragmentWrapper(fragmentsContainer, fragment, bundle.getSymbolicName(),
								URIHelper.constructPlatformURI(bundle), checkExist); // $NON-NLS-1$
					}
				}
			}
		} else {
			logger.error("Model-Fragment header value \"{0}\" in bundle \\\"{1}\\\" is invalid", //$NON-NLS-1$
					fragmentHeader, bundle.getSymbolicName());
		}

		return null;
	}

