				if (migrationProcessor != null && isFirstE4WorkbenchRun(appModel)
						&& migrationProcessor.isLegacyWorkbenchDetected()) {
					try {
						WorkbenchPlugin
								.log(StatusUtil.newStatus(IStatus.INFO, "Workbench migration started", null)); //$NON-NLS-1$
						migrationProcessor.migrate();
					} catch (Exception e2) {
						WorkbenchPlugin.log("Workbench migration failed", e2); //$NON-NLS-1$
						migrationProcessor.restoreDefaultModel();
