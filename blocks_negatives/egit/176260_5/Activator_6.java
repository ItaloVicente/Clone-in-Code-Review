		GitProjectData.attachToWorkspace();
		setupResourceRefresh();

		repositoryUtil = new RepositoryUtil();

		secureStore = new EGitSecureStore(SecurePreferencesFactory.getDefault());

		registerAutoShareProjects();
		registerAutoIgnoreDerivedResources();
		registerPreDeleteResourceChangeListener();
		registerMergeStrategyRegistryListener();
		registerBuiltinLFS();
	}

	private void migratePreferences() {
		IEclipsePreferences corePrefs = InstanceScope.INSTANCE
				.getNode(PLUGIN_ID);
		boolean changed = false;
		IEclipsePreferences uiPrefs = InstanceScope.INSTANCE
		int timeout = corePrefs
				.getInt(GitCorePreferences.core_remoteConnectionTimeout, -1);
		if (timeout < 0) {
			timeout = uiPrefs.getInt(old_ui_preference_key, -1);
			if (timeout > 0) {
				corePrefs.putInt(
						GitCorePreferences.core_remoteConnectionTimeout,
						timeout);
				uiPrefs.remove(old_ui_preference_key);
				changed = true;
			}
		}
		if (changed) {
			try {
				corePrefs.flush();
				uiPrefs.flush();
			} catch (BackingStoreException e) {
				logError(e.getMessage(), e);
			}
		}
	}

	private void setupHttp() {
		String sshClient = Platform.getPreferencesService().getString(PLUGIN_ID,
				GitCorePreferences.core_httpClient, "jdk", null); //$NON-NLS-1$
		if (HttpClientType.APACHE.name().equalsIgnoreCase(sshClient)) {
			HttpTransport.setConnectionFactory(new HttpClientConnectionFactory());
		} else {
			if (!HttpClientType.JDK.name().equalsIgnoreCase(sshClient)) {
				logWarning(
						MessageFormat.format(
								CoreText.Activator_HttpClientUnknown, sshClient),
						null);
			}
			HttpTransport.setConnectionFactory(new JDKHttpConnectionFactory());
		}
	}

	private void setupProxy() {
		IProxyService proxy = getProxyService();
		if (proxy != null) {
			ProxySelector.setDefault(new EclipseProxySelector(proxy));
			Authenticator.setDefault(new EclipseAuthenticator(proxy));
		}
	}

	private void setupResourceRefresh() {
		refreshHandle = repositoryCache.getGlobalListenerList()
				.addWorkingTreeModifiedListener(new ResourceRefreshHandler());
	}

	private void registerPreDeleteResourceChangeListener() {
		if (preDeleteProjectListener == null) {
			preDeleteProjectListener = new IResourceChangeListener() {

				@Override
				public void resourceChanged(IResourceChangeEvent event) {
					IResource resource = event.getResource();
					if (resource instanceof IProject) {
						IProject project = (IProject) resource;
						if (project.isAccessible()) {
							if (ResourceUtil.isSharedWithGit(project)) {
								IResource dotGit = project
										.findMember(Constants.DOT_GIT);
								if (dotGit != null && dotGit
										.getType() == IResource.FOLDER) {
									GitProjectData.reconfigureWindowCache();
								}
							}
						} else {
							IPath locationPath = project.getLocation();
							if (locationPath != null) {
								File locationDir = locationPath.toFile();
								File dotGit = new File(locationDir,
										Constants.DOT_GIT);
								if (dotGit.exists() && dotGit.isDirectory()) {
									GitProjectData.reconfigureWindowCache();
								}
							}
						}
					}
				}
			};
			ResourcesPlugin.getWorkspace().addResourceChangeListener(preDeleteProjectListener, IResourceChangeEvent.PRE_DELETE);
		}
	}

	private void registerBuiltinLFS() {
			Class<?> lfs;
			try {
				if (lfs != null) {
				}
			} catch (ClassNotFoundException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e1) {
				logWarning(CoreText.Activator_noBuiltinLfsSupportDetected, e1);
			}
		}
	}

	@Override
	public void optionsChanged(DebugOptions options) {
		GitTraceLocation.initializeFromOptions(options, isDebugging());
	}

	/**
	 * Provides the 3-way merge strategy to use according to the user's
	 * preferences. The preferred merge strategy is JGit's default merge
	 * strategy unless the user has explicitly chosen a different strategy among
	 * the registered strategies.
	 *
	 * @return The MergeStrategy to use, can be {@code null}, in which case the
	 *         default merge strategy should be used as defined by JGit.
	 * @since 4.1
	 */
	public MergeStrategy getPreferredMergeStrategy() {
		final IEclipsePreferences prefs = InstanceScope.INSTANCE
				.getNode(PLUGIN_ID);
		String preferredMergeStrategyKey = prefs.get(
				GitCorePreferences.core_preferredMergeStrategy, null);

		if (preferredMergeStrategyKey == null
				|| preferredMergeStrategyKey.isEmpty()) {
			final IEclipsePreferences defaultPrefs = DefaultScope.INSTANCE
					.getNode(PLUGIN_ID);
			preferredMergeStrategyKey = defaultPrefs.get(
					GitCorePreferences.core_preferredMergeStrategy, null);
		}
		if (preferredMergeStrategyKey != null
				&& !preferredMergeStrategyKey.isEmpty()
				&& !GitCorePreferences.core_preferredMergeStrategy_Default
						.equals(preferredMergeStrategyKey)) {
			MergeStrategy result = MergeStrategy.get(preferredMergeStrategyKey);
			if (result != null) {
				return result;
			}
			logError(NLS.bind(CoreText.Activator_invalidPreferredMergeStrategy,
					preferredMergeStrategyKey), null);
		}
		return null;
	}

	/**
	 * @return Provides a read-only view of the registered MergeStrategies
	 *         available.
	 * @since 4.1
	 */
	public Collection<MergeStrategyDescriptor> getRegisteredMergeStrategies() {
		if (mergeStrategyRegistryListener == null) {
			return Collections.emptyList();
		}
		return mergeStrategyRegistryListener.getStrategies();
	}

	private void registerMergeStrategyRegistryListener() {
		mergeStrategyRegistryListener = new MergeStrategyRegistryListener(
				Platform.getExtensionRegistry());
		Platform.getExtensionRegistry().addListener(
				mergeStrategyRegistryListener,
	}

	/**
	 * @return cache for Repository objects
	 */
	public RepositoryCache getRepositoryCache() {
		return repositoryCache;
	}

	/**
	 *  @return cache for index diffs
	 */
	public IndexDiffCache getIndexDiffCache() {
		return indexDiffCache;
	}

	/**
	 * @return the {@link RepositoryUtil} instance
	 */
	public RepositoryUtil getRepositoryUtil() {
		return repositoryUtil;
	}

	/**
	 * @return the secure store
	 */
	public EGitSecureStore getSecureStore() {
		return secureStore;
	}

	/**
	 * Obtains the {@link IProxyService}.
	 *
	 * @return the {@link IProxyService} or {@code null} if none is available.
	 */
	public IProxyService getProxyService() {
		return proxyServiceTracker.getService();
