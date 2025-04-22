	private static class QuickAccessProviderExtensionProxy extends QuickAccessProvider {
		private static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$
		private static final String REQUIRES_UI_ACCESS_ATTRIBUTE = "requiresUIAccess"; //$NON-NLS-1$

		private final Bundle bundle;
		private final IConfigurationElement extension;
		private IQuickAccessComputer computer;

		public QuickAccessProviderExtensionProxy(IConfigurationElement extension) {
			this.bundle = Platform.getBundle(extension.getContributor().getName());
			this.extension = extension;
		}

		private boolean canDelegate() {
			if (bundle != null && bundle.getState() == Bundle.ACTIVE) {
				if (computer == null) {
