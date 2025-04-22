			if (generator != null) {
				if (generator instanceof IMarkerResolutionGenerator2) {
					if (((IMarkerResolutionGenerator2) generator)
							.hasResolutions(marker)) {
						return true;
					}
				} else {
					IMarkerResolution[] resolutions = generator
							.getResolutions(marker);
					if (resolutions == null) {
						StatusManager.getManager().handle(
								new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, IStatus.ERROR,
										": getResolutions(IMarker) must not return null",//$NON-NLS-1$
										null),StatusManager.LOG);

						return false;
					} else if (resolutions.length > 0) {
						return true;
