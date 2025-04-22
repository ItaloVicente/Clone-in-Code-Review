		IEclipsePreferences d = DefaultScope.INSTANCE
				.getNode(Activator.PLUGIN_ID);
		IEclipsePreferences p = InstanceScope.INSTANCE
				.getNode(Activator.PLUGIN_ID);
		boolean autoStageMoves = p.getBoolean(
				GitCorePreferences.core_autoStageMoves,
				d.getBoolean(GitCorePreferences.core_autoStageMoves, false));
		return autoStageMoves;
	}
	private static class IgnoreDerivedResources implements
			IResourceChangeListener {

		public void stop() {
			Job.getJobManager().cancel(JobFamilies.AUTO_IGNORE);
			try {
				Job.getJobManager().join(JobFamilies.AUTO_IGNORE,
						new NullProgressMonitor());
			} catch (OperationCanceledException e) {
			} catch (InterruptedException e) {
				logError(e.getLocalizedMessage(), e);
			}
		}

		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			try {
				IResourceDelta d = event.getDelta();
				if (d == null || !autoIgnoreDerived()) {
					return;
				}

				final Set<IPath> toBeIgnored = new LinkedHashSet<>();

				d.accept(new IResourceDeltaVisitor() {

					@Override
					public boolean visit(IResourceDelta delta)
							throws CoreException {
						if ((delta.getKind() & (IResourceDelta.ADDED | IResourceDelta.CHANGED)) == 0)
							return false;
						int flags = delta.getFlags();
						if ((flags != 0)
								&& ((flags & IResourceDelta.DERIVED_CHANGED) == 0))
							return false;

						final IResource r = delta.getResource();
						if ((r.getProject() != null)
								&& (RepositoryMapping.getMapping(r) == null))
							return false;
						if (r.isTeamPrivateMember())
							return false;

						if (r.isDerived()) {
							try {
								IPath location = r.getLocation();
								if (RepositoryUtil.canBeAutoIgnored(location)) {
									toBeIgnored.add(location);
								}
							} catch (IOException e) {
								logError(
										MessageFormat.format(
												CoreText.Activator_ignoreResourceFailed,
												r.getFullPath()), e);
							}
							return false;
						}
						return true;
					}
				});
				if (toBeIgnored.size() > 0)
					JobUtil.scheduleUserJob(new IgnoreOperation(toBeIgnored),
							CoreText.Activator_autoIgnoreDerivedResources,
							JobFamilies.AUTO_IGNORE);
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
				return;
			}
		}
	}

	/**
	 * Describes a MergeStrategy which can be registered with the mergeStrategy
	 * extension point.
	 *
	 * @since 4.1
	 */
	public static class MergeStrategyDescriptor {
		private final String name;

		private final String label;

		private final Class<?> implementedBy;

		/**
		 * @param name
		 *            The referred strategy's name, to use for retrieving the
		 *            strategy from MergeRegistry via
		 *            {@link MergeStrategy#get(String)}
		 * @param label
		 *            The label to display to users so they can select the
		 *            strategy they need
		 * @param implementedBy
		 *            The class of the MergeStrategy registered through the
		 *            mergeStrategy extension point
		 */
		public MergeStrategyDescriptor(String name, String label,
				Class<?> implementedBy) {
			this.name = name;
			this.label = label;
			this.implementedBy = implementedBy;
		}

		/**
		 * @return The actual strategy's name, which can be used to retrieve
		 *         that actual strategy via {@link MergeStrategy#get(String)}.
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return The strategy label, for display purposes.
		 */
		public String getLabel() {
			return label;
		}

		/**
		 * @return The class of the MergeStrategy registered through the
		 *         mergeStrategy extension point.
		 */
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
					if (ext instanceof MergeStrategy) {
						MergeStrategy strategy = (MergeStrategy) ext;
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

		/**
		 * Checks whether it's possible to register the provided strategy with
		 * the given name
		 *
		 * @param name
		 *            Name to use to register the strategy
		 * @param strategy
		 *            Strategy to register
		 * @return <code>true</code> if the name is neither null nor empty, no
		 *         other strategy is already register for the same name, and the
		 *         name is not one of the core JGit strategies. If the given
		 *         name is that of a core JGit strategy, the method will return
		 *         <code>true</code> only if the strategy is the matching JGit
		 *         strategy for that name.
		 */
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

	/**
	 * A system reader that hides certain global git environment variables from
	 * JGit.
	 */
	private static class EclipseSystemReader extends SystemReader {

		/**
		 * Hide these variables lest JGit tries to use them for different
		 * repositories.
		 */
		private static final String[] HIDDEN_VARIABLES = {
				Constants.GIT_DIR_KEY, Constants.GIT_WORK_TREE_KEY,
				Constants.GIT_OBJECT_DIRECTORY_KEY,
				Constants.GIT_INDEX_FILE_KEY,
				Constants.GIT_ALTERNATE_OBJECT_DIRECTORIES_KEY };

		private final SystemReader delegate;

		public EclipseSystemReader(SystemReader delegate) {
			this.delegate = delegate;
		}

		@Override
		public String getenv(String variable) {
			String result = delegate.getenv(variable);
			if (result == null) {
				return result;
			}
			boolean isWin = isWindows();
			for (String gitvar : HIDDEN_VARIABLES) {
				if (isWin && gitvar.equalsIgnoreCase(variable)
						|| !isWin && gitvar.equals(variable)) {
					return null;
				}
			}
			return result;
		}

		@Override
		public String getHostname() {
			return delegate.getHostname();
		}

		@Override
		public String getProperty(String key) {
			return delegate.getProperty(key);
		}

		@Override
		public FileBasedConfig openUserConfig(Config parent, FS fs) {
			return delegate.openUserConfig(parent, fs);
		}

		@Override
		public FileBasedConfig openJGitConfig(Config parent, FS fs) {
			return delegate.openJGitConfig(parent, fs);
		}

		@Override
		public FileBasedConfig openSystemConfig(Config parent, FS fs) {
			return delegate.openSystemConfig(parent, fs);
		}

		@Override
		public long getCurrentTime() {
			return delegate.getCurrentTime();
		}

		@Override
		public int getTimezone(long when) {
			return delegate.getTimezone(when);
		}

		@Override
		public StoredConfig getUserConfig()
				throws IOException, ConfigInvalidException {
			return delegate.getUserConfig();
		}

		@Override
		public StoredConfig getJGitConfig()
				throws IOException, ConfigInvalidException {
			return delegate.getJGitConfig();
		}

		@Override
		public StoredConfig getSystemConfig()
				throws IOException, ConfigInvalidException {
			return delegate.getSystemConfig();
		}
