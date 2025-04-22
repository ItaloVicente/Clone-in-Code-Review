
		@Override
		public Image getImage(Object element) {
			Spec spec = (Spec) element;
			if (spec.getPredefined()) {
				return JFaceResources.getImage(ProgressManager.BLOCKED_JOB_KEY);
			}
			return null;
		}
