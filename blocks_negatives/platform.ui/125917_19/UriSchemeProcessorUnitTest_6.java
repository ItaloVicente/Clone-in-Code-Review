	private final class ConfigElementMock implements IConfigurationElement {
		private final String uriScheme;
		private final Object handler;

		public int extensionCreatedCount = 0;

		private ConfigElementMock(String uriScheme, Object handler) {
			this.uriScheme = uriScheme;
			this.handler = handler;
		}

		@Override
		public boolean isValid() {
			return false;
		}

		@Override
		public String getValueAsIs() throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public String getValue(String locale) throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public String getValue() throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public Object getParent() throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public String getNamespaceIdentifier() throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public String getNamespace() throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public String getName() throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public int getHandleId() {
			return 0;
		}

		@Override
		public IExtension getDeclaringExtension() throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public IContributor getContributor() throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public IConfigurationElement[] getChildren(String name) throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public IConfigurationElement[] getChildren() throws InvalidRegistryObjectException {
			return null;
		}

		@Override
		public String[] getAttributeNames() throws InvalidRegistryObjectException {
			return null;
		}
