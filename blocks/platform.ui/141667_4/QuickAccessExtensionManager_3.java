				}
				return computer != null;
			}
			return false;
		}

		@Override
		public boolean requiresUiAccess() {
			return Boolean.parseBoolean(extension.getAttribute(REQUIRES_UI_ACCESS_ATTRIBUTE));
		}

		@Override
		public String getName() {
			return extension.getAttribute(NAME_ATTRIBUTE);
		}

		@Override
		public String getId() {
			return bundle.getSymbolicName() + '/' + extension.getAttribute(IWorkbenchRegistryConstants.ATT_CLASS);
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			return null;
		}

		@Override
		public boolean isAlwaysPresent() {
			return false;
		}

		@Override
		public QuickAccessElement[] getElementsSorted() {
			if (canDelegate()) {
				if (computer.needsRefresh()) {
					reset();
				}
				return super.getElementsSorted();
			}
			return new QuickAccessElement[0];
		}

		@Override
		public QuickAccessElement[] getElements() {
			if (canDelegate()) {
				return computer.computeElements();
			}
			return new QuickAccessElement[0];
		}

		@Override
		protected void doReset() {
			if (canDelegate()) {
				computer.resetState();
			}
		}

	}

	public static Collection<QuickAccessProvider> getProviders() {
		return Arrays.stream(Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_POINT_ID))
				.filter(element -> COMPUTER_TAG.equals(element.getName())).map(QuickAccessProviderExtensionProxy::new)
