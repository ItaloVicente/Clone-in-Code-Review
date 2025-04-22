							IMarkerResolution[] res = generator.getResolutions(marker);
							if (res != null) {
								for (IMarkerResolution generatedResolution : res) {
									resolutions.add(generatedResolution);
								}
							} else {
								StatusManager.getManager()
										.handle(new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH,
												IStatus.ERROR, "Failure in " + generator.getClass().getName() + //$NON-NLS-1$
														" from plugin " + element.getContributor().getName() + //$NON-NLS-1$
														": getResolutions(IMarker) must not return null", //$NON-NLS-1$
												null), StatusManager.LOG);
							}
