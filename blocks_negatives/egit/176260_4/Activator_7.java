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
