	private class EvaluationCache implements VisibilityListener {

		private final Map evaluations/* <Object, NavigatorContentDescriptor[]> */= new HashMap();
		private final Map evaluationsWithOverrides/*<Object, NavigatorContentDescriptor[]>*/ = new HashMap();

		EvaluationCache(VisibilityAssistant anAssistant) {
			anAssistant.addListener(this);
		}

		protected final NavigatorContentDescriptor[] getDescriptors(Object anElement) {
			return getDescriptors(anElement, true);
		}

		protected final void setDescriptors(Object anElement, NavigatorContentDescriptor[] theDescriptors) {
			setDescriptors(anElement, theDescriptors, true);
		}

		protected final NavigatorContentDescriptor[] getDescriptors(Object anElement, boolean toComputeOverrides) {

			if (anElement == null)
				return null;

			NavigatorContentDescriptor[] cachedDescriptors = null;
			if (toComputeOverrides) {
				SoftReference cache = (SoftReference) evaluations.get(anElement);
				if (cache != null && (cachedDescriptors = (NavigatorContentDescriptor[]) cache.get()) == null)
					evaluations.remove(anElement);
				return cachedDescriptors;
			}
			SoftReference cache = (SoftReference) evaluationsWithOverrides.get(anElement);
			if (cache != null && (cachedDescriptors = (NavigatorContentDescriptor[]) cache.get()) == null)
				evaluationsWithOverrides.remove(anElement);
			return cachedDescriptors;

		}

		protected final void setDescriptors(Object anElement, NavigatorContentDescriptor[] theDescriptors, boolean toComputeOverrides) {
			if (anElement != null) {
				if (toComputeOverrides)
					evaluations.put(new EvalutationReference(anElement), new SoftReference(theDescriptors));
				else
					evaluationsWithOverrides.put(new EvalutationReference(anElement), new SoftReference(theDescriptors));
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.ui.internal.navigator.VisibilityAssistant.VisibilityListener#onVisibilityOrActivationChange()
		 */
		public void onVisibilityOrActivationChange() {
			evaluations.clear();
			evaluationsWithOverrides.clear();
		}
	}

	/* Map of (VisibilityAssistant, EvaluationCache)-pairs */
	private final Map cachedTriggerPointEvaluations = new WeakHashMap();

	/* Map of (VisibilityAssistant, EvaluationCache)-pairs */
	private final Map cachedPossibleChildrenEvaluations = new WeakHashMap();

