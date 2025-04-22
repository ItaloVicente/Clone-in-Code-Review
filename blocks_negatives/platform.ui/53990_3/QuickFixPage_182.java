				getWizard().getContainer().run(false, true, new IRunnableWithProgress() {

					@Override
					public void run(IProgressMonitor monitor1) {
						IMarker[] markers = new IMarker[checked.length];
						System.arraycopy(checked, 0, markers, 0, checked.length);
						((WorkbenchMarkerResolution) resolution).run(markers, monitor1);
					}

