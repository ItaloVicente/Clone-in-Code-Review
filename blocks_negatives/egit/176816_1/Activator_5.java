	private void registerTemplateVariableResolvers() {
		if (!hasJavaPlugin()) {
			return;
		}
		WorkbenchJob job = new WorkbenchJob(
				UIText.Activator_setupJdtTemplateResolver) {

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					final ContextTypeRegistry codeTemplateContextRegistry = JavaPlugin
							.getDefault().getCodeTemplateContextRegistry();
					final Iterator<?> ctIter = codeTemplateContextRegistry
							.contextTypes();

					while (ctIter.hasNext()) {
						final TemplateContextType contextType = (TemplateContextType) ctIter
								.next();
						contextType.addResolver(new GitTemplateVariableResolver(
								"git_config", //$NON-NLS-1$
								UIText.GitTemplateVariableResolver_GitConfigDescription));
					}
				} catch (Throwable e) {
					logError("Cannot register git support for Java templates", //$NON-NLS-1$
							e);
				}
				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.setUser(false);
		job.schedule();
	}

	/**
	 * @return true if at least one Eclipse window is active
	 */
	static boolean isActive() {
		return getDefault().uiIsActive;
	}


	private void setupFocusHandling() {
		focusListener = new IWindowListener() {

			private void updateUiState() {
				Display.getCurrent().asyncExec(new Runnable() {
					@Override
					public void run() {
						boolean wasActive = uiIsActive;
						uiIsActive = Display.getCurrent().getActiveShell() != null;
						if (uiIsActive != wasActive
								&& GitTraceLocation.REPOSITORYCHANGESCANNER
										.isActive())
							traceUiIsActive();
					}

					private void traceUiIsActive() {
						StringBuilder message = new StringBuilder(
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.REPOSITORYCHANGESCANNER
										.getLocation(), message.toString());
					}
				});
			}

			@Override
			public void windowOpened(IWorkbenchWindow window) {
				updateUiState();
			}

			@Override
			public void windowDeactivated(IWorkbenchWindow window) {
				updateUiState();
			}

			@Override
			public void windowClosed(IWorkbenchWindow window) {
				updateUiState();
			}

			@Override
			public void windowActivated(IWorkbenchWindow window) {
				updateUiState();
				rcs.schedule(500);
			}
		};
		Job job = new Job(UIText.Activator_setupFocusListener) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				if (PlatformUI.isWorkbenchRunning()) {
					PlatformUI.getWorkbench().addWindowListener(focusListener);
					registerCoreJobFamilyIcons();
				} else {
					schedule(1000L);
				}
				return Status.OK_STATUS;
			}

			/**
			 * register progress icons for jobs from core plugin
			 */
			private void registerCoreJobFamilyIcons() {
				PlatformUI.getWorkbench().getDisplay()
						.asyncExec(() -> {
							IProgressService service = PlatformUI.getWorkbench()
									.getProgressService();

							service.registerIconForFamily(UIIcons.PULL,
									JobFamilies.PULL);
							service.registerIconForFamily(UIIcons.REPOSITORY,
									JobFamilies.AUTO_IGNORE);
							service.registerIconForFamily(UIIcons.REPOSITORY,
									JobFamilies.AUTO_SHARE);
							service.registerIconForFamily(UIIcons.REPOSITORY,
									JobFamilies.INDEX_DIFF_CACHE_UPDATE);
							service.registerIconForFamily(UIIcons.REPOSITORY,
									JobFamilies.REPOSITORY_CHANGED);
						});
			}
		};
		job.setSystem(true);
		job.setUser(false);
		job.schedule();
	}

	@Override
	public void optionsChanged(DebugOptions options) {
		debugOptions = options;
		GitTraceLocation.initializeFromOptions(options, isDebugging());
	}

	/**
	 * @return the {@link DebugOptions}
	 */
	public DebugOptions getDebugOptions() {
		return debugOptions;
	}

