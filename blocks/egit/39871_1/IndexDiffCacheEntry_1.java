				if (monitor.isCanceled())
					return Status.CANCEL_STATUS;

				if (files.size() > RESOURCE_LIST_UPDATE_LIMIT) {
					scheduleReloadJob("Too many resources changed"); //$NON-NLS-1$
					return Status.CANCEL_STATUS;
				}

