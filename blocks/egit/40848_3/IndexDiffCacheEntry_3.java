				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}

				if (shouldReload(files)) {
					scheduleReloadJob("Too many resources changed: " + files.size()); //$NON-NLS-1$
					return Status.CANCEL_STATUS;
				}

