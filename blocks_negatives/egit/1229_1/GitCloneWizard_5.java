
			};
			job.setUser(true);
			job.schedule();
			return true;
		} else {
			try {
				getContainer().run(true, true, new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {
						op.run(monitor);
						if (monitor.isCanceled())
							throw new InterruptedException();
					}
				});

				cloneSource.saveUriInPrefs();
				config.addConfiguredRepository(op.getGitDir());
				return true;
			} catch (InterruptedException e) {
				MessageDialog.openInformation(getShell(),
						UIText.GitCloneWizard_CloneFailedHeading,
						UIText.GitCloneWizard_CloneCanceledMessage);
				return false;
			} catch (Exception e) {
				Activator.handleError(UIText.GitCloneWizard_CloneFailedHeading,
						e, true);
				return false;
