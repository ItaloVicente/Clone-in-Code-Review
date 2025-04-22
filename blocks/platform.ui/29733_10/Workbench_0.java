					MApplication appModel = e4Workbench.getApplication();
					IEclipseContext context = e4Workbench.getContext();

					WorkbenchMigrationProcessor migrationProcessor = null;
					try {
						migrationProcessor = ContextInjectionFactory.make(
							WorkbenchMigrationProcessor.class, context);
					} catch (@SuppressWarnings("restriction") InjectionException e) {
						WorkbenchPlugin.log(e);
					}
					
					if (migrationProcessor != null && isFirstE4WorkbenchRun(appModel)
							&& migrationProcessor.legacyWorkbenchDetected()) {
						try {
							WorkbenchPlugin.log(StatusUtil.newStatus(IStatus.INFO, "Workbench migration started", null)); //$NON-NLS-1$
							migrationProcessor.process();
						} catch (Exception e) {
							WorkbenchPlugin.log("Workbench migration failed", e); //$NON-NLS-1$
							migrationProcessor.restoreDefaultModel();
						}
					}

