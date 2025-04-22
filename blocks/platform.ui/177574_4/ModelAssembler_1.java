	private class ModelFragmentBundleTracker implements BundleTrackerCustomizer<FragmentWrapperElementMapping> {

		@Override
		public FragmentWrapperElementMapping addingBundle(Bundle bundle, BundleEvent event) {
			if (bundle.getHeaders("").get(MODEL_FRAGMENT_HEADER) != null) { //$NON-NLS-1$
				ModelFragmentWrapper wrapper = getModelFragmentWrapperFromBundle(bundle, ModelAssembler.this.initial);
				if (wrapper != null) {
					List<MApplicationElement> elements = new ArrayList<>(wrapper.getModelFragment().getElements());

					if (processModelExecuted) {
						uiSync.asyncExec(() -> processFragmentWrappers(Arrays.asList(wrapper)));
					}
