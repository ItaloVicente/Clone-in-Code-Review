				getWizard().getContainer().run(false, true,
						new IRunnableWithProgress() {
							/*
							 * (non-Javadoc)
							 *
							 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
							 */
							@Override
							public void run(IProgressMonitor monitor) {
								IMarker[] markers = new IMarker[checked.length];
								System.arraycopy(checked, 0, markers, 0,
										checked.length);
								((WorkbenchMarkerResolution) resolution).run(
										markers, monitor);
							}
