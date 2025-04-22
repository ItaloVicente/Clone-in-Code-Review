				}, workspace.getRuleFactory().refreshRule(workspace.getRoot()),
						IWorkspace.AVOID_UPDATE, monitor);
			} catch (CoreException e) {
				handleError(UIText.Activator_refreshFailed, e, false);
				return new Status(IStatus.ERROR, getPluginId(), e.getMessage());
			}

			if (!monitor.isCanceled()) {
				synchronized (repositoriesChanged) {
					if (!repositoriesChanged.isEmpty()) {
						schedule(100);
