		Bundle[] bundles = this.bundleContext.getBundles();
		Arrays.stream(bundles)
				.filter(b -> (b.getState() == Bundle.RESOLVED || b.getState() == Bundle.STARTING
						|| b.getState() == Bundle.ACTIVE)
						&& b.getHeaders("").get(MODEL_FRAGMENT_HEADER) != null) //$NON-NLS-1$
				.forEach(b -> {
					ModelFragmentWrapper wrapper = getModelFragmentWrapperFromBundle(b, initial);
					if (wrapper != null) {
						wrappers.add(wrapper);
					}
				});

