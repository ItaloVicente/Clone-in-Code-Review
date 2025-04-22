
	public static class MergeStrategyDescriptor {
		private final String name;

		private final String label;

		public MergeStrategyDescriptor(String name, String label) {
			this.name = name;
			this.label = label;
		}

		public String getName() {
			return name;
		}

		public String getLabel() {
			return label;
		}
	}

	private static class MergeStrategyRegistryListener implements
			IRegistryEventListener {

		private Map<String, MergeStrategyDescriptor> strategies;

		public MergeStrategyRegistryListener(IExtensionRegistry registry) {
			strategies = new LinkedHashMap<>();
			IConfigurationElement[] elements = registry
					.getConfigurationElementsFor("org.eclipse.egit.core.mergeStrategy"); //$NON-NLS-1$
			loadMergeStrategies(elements);
		}

		public Collection<MergeStrategyDescriptor> getStrategies() {
			return Collections.unmodifiableCollection(strategies.values());
		}

		@Override
		public void added(IExtension[] extensions) {
			for (IExtension extension : extensions) {
				loadMergeStrategies(extension.getConfigurationElements());
			}
		}

		@Override
		public void added(IExtensionPoint[] extensionPoints) {
		}

		@Override
		public void removed(IExtension[] extensions) {
			for (IExtension extension : extensions) {
				for (IConfigurationElement element : extension
						.getConfigurationElements()) {
					try {
						Object ext = element.createExecutableExtension("class"); //$NON-NLS-1$
						if (ext instanceof MergeStrategy) {
							MergeStrategy strategy = (MergeStrategy) ext;
							strategies.remove(strategy.getName());
						}
					} catch (CoreException e) {
						Activator.logError(CoreText.MergeStrategy_UnloadError,
								e);
					}
				}
			}
		}

		@Override
		public void removed(IExtensionPoint[] extensionPoints) {
		}

		private void loadMergeStrategies(IConfigurationElement[] elements) {
			for (IConfigurationElement element : elements) {
				try {
					Object ext = element.createExecutableExtension("class"); //$NON-NLS-1$
					if (ext instanceof MergeStrategy) {
						MergeStrategy strategy = (MergeStrategy) ext;
						String name = strategy.getName();
						if (name == null || name.isEmpty()) {
							Activator.logWarning(NLS.bind(
									CoreText.MergeStrategy_InvalidName,
									strategy.getClass()), null);
						} else if (strategies.containsKey(name)) {
							Activator.logWarning(NLS.bind(
									CoreText.MergeStrategy_DuplicateName,
									strategy.getName(), strategy.getClass()),
									null);
						} else {
							if (MergeStrategy.get(name) == null) {
								MergeStrategy.register(name, strategy);
							}
							strategies.put(name, new MergeStrategyDescriptor(
									name, element.getAttribute("label"))); //$NON-NLS-1$
						}
					}
				} catch (CoreException e) {
					Activator.logError(CoreText.MergeStrategy_LoadError, e);
				}
			}
		}
	}
