					MApplication appModel = e4Workbench.getApplication();
					IEclipseContext context = e4Workbench.getContext();

					WorkbenchMigrationProcessor migrationProcessor = null;
					if (ImportExportPespectiveHandler.isImpExpEnabled()) {
						try {
							migrationProcessor = ContextInjectionFactory.make(
									WorkbenchMigrationProcessor.class, context);
						} catch (@SuppressWarnings("restriction") InjectionException e) {
							WorkbenchPlugin.log(e);
						}
					}

					if (migrationProcessor != null && isFirstE4WorkbenchRun(appModel)
							&& migrationProcessor.isLegacyWorkbenchDetected()) {
						try {
							WorkbenchPlugin.log(StatusUtil.newStatus(IStatus.INFO,
									"Workbench migration started", null)); //$NON-NLS-1$
							migrationProcessor.migrate();
						} catch (Exception e) {
							WorkbenchPlugin.log("Workbench migration failed", e); //$NON-NLS-1$
							migrationProcessor.restoreDefaultModel();
						}
					}

