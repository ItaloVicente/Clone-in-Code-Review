		if (checked.length == 0) {
			return;
		}
		if (resolution instanceof WorkbenchMarkerResolution) {

			try {
				getWizard().getContainer().run(false, true, monitor1 -> {
					IMarker[] markers = new IMarker[checked.length];
					System.arraycopy(checked, 0, markers, 0, checked.length);
					((WorkbenchMarkerResolution) resolution).run(markers, monitor1);
				});
			} catch (InvocationTargetException | InterruptedException e) {
				StatusManager.getManager().handle(MarkerSupportInternalUtilities.errorFor(e));
			}

		} else {

			try {
				Translation translation = new Translation();
				getWizard().getContainer().run(false, true, monitor1 -> {
					monitor1.beginTask(MarkerMessages.MarkerResolutionDialog_Fixing, checked.length);
					for (Object checkedElement : checked) {
						getShell().getDisplay().readAndDispatch();
						if (monitor1.isCanceled()) {
							return;
						}
						IMarker marker = (IMarker) checkedElement;
						resolution.run(marker);
						monitor1.worked(1);
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				StatusManager.getManager().handle(
						MarkerSupportInternalUtilities.errorFor(e));
			}

		}
