	private class ModelFragmentBundleTracker implements BundleTrackerCustomizer<List<FragmentWrapperElementMapping>> {

		@Override
		public List<FragmentWrapperElementMapping> addingBundle(Bundle bundle, BundleEvent event) {
			if (bundle.getHeaders("").get(MODEL_FRAGMENT_HEADER) != null) { //$NON-NLS-1$
				List<ModelFragmentWrapper> wrappers = getModelFragmentWrapperFromBundle(bundle,
						ModelAssembler.this.initial);

				List<FragmentWrapperElementMapping> mappings = wrappers.stream().map(w -> {
					FragmentWrapperElementMapping mapping = new FragmentWrapperElementMapping();
					mapping.wrapper = w;
					mapping.elements = new ArrayList<>(w.getModelFragment().getElements());
					return mapping;
				}).collect(Collectors.toList());

				if (processModelExecuted) {
					uiSync.asyncExec(() -> processFragmentWrappers(wrappers));
				}
