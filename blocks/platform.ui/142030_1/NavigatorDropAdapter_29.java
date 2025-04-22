				}
			}
		}
		return selectedResources.toArray(new IResource[selectedResources.size()]);
	}

	private Shell getShell() {
		return getViewer().getControl().getShell();
	}

	private IStatus info(String message) {
		return new Status(IStatus.INFO, PlatformUI.PLUGIN_ID, 0, message, null);
	}

	private void mergeStatus(MultiStatus status, IStatus toMerge) {
		if (!toMerge.isOK()) {
			status.merge(toMerge);
		}
	}

	private IStatus ok() {
		return new Status(IStatus.OK, PlatformUI.PLUGIN_ID, 0, ResourceNavigatorMessages.DropAdapter_ok, null);
	}

	private void openError(IStatus status) {
		if (status == null) {
