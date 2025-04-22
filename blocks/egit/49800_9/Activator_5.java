
	public static class MergeStrategyDescriptor {
		private final String name;

		private final String label;

		private final Class<?> implementedBy;

		public MergeStrategyDescriptor(String name, String label,
				Class<?> implementedBy) {
			this.name = name;
			this.label = label;
			this.implementedBy = implementedBy;
		}

		public String getName() {
			return name;
		}

		public String getLabel() {
			return label;
		}

		public Class<?> getImplementedBy() {
			return implementedBy;
		}
	}

	private static class MergeStrategyRegistryListener implements
			IRegistryEventListener {

		private Map<String, MergeStrategyDescriptor> strategies;

		private MergeStrategyRegistryListener(IExtensionRegistry registry) {
			strategies = new LinkedHashMap<>();
			IConfigurationElement[] elements = registry
					.getConfigurationElementsFor("org.eclipse.egit.core.mergeStrategy"); //$NON-NLS-1$
			loadMergeStrategies(elements);
		}

		private Collection<MergeStrategyDescriptor> getStrategies() {
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
						String name = element.getAttribute("name"); //$NON-NLS-1$
						if (name == null || name.isEmpty()) {
							name = strategy.getName();
						}
						if (canRegister(name, strategy)) {
							if (MergeStrategy.get(name) == null) {
								MergeStrategy.register(name, strategy);
							}
							strategies
									.put(name,
											new MergeStrategyDescriptor(
													name,
													element.getAttribute("label"), //$NON-NLS-1$
													strategy.getClass()));
						}
					}
				} catch (CoreException e) {
					Activator.logError(CoreText.MergeStrategy_LoadError, e);
				}
			}
		}

		private boolean canRegister(String name, MergeStrategy strategy) {
			boolean result = true;
			if (name == null || name.isEmpty()) {
				Activator.logError(
						NLS.bind(CoreText.MergeStrategy_MissingName,
								strategy.getClass()), null);
				result = false;
			} else if (strategies.containsKey(name)) {
				Activator.logError(NLS.bind(
						CoreText.MergeStrategy_DuplicateName, new Object[] {
								name, strategies.get(name).getImplementedBy(),
								strategy.getClass() }), null);
				result = false;
			} else if (MergeStrategy.get(name) != null
					&& MergeStrategy.get(name) != strategy) {
				Activator.logError(NLS.bind(
						CoreText.MergeStrategy_ReservedName, new Object[] {
								name, MergeStrategy.get(name).getClass(),
								strategy.getClass() }), null);
				result = false;
			}
			return result;
		}
	}
