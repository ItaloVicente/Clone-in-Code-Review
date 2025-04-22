					if (migrationProcessor != null && migrationProcessor.isWorkbenchMigrated()) {
						migrationProcessor.updatePartsAfterMigration(WorkbenchPlugin.getDefault()
								.getPerspectiveRegistry(), WorkbenchPlugin.getDefault()
								.getViewRegistry());
						WorkbenchPlugin.log(StatusUtil.newStatus(IStatus.INFO, "Workbench migration finished", null)); //$NON-NLS-1$
					}
