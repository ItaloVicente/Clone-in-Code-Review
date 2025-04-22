					setCanceled(true);
					if (fStopButton != null) {
						fStopButton.setEnabled(false);
					}
				}
			});
			final Image stopImage = ImageDescriptor
					.createFromFile(ProgressMonitorPart.class,
							"images/stop.gif").createImage(getDisplay()); //$NON-NLS-1$
			final Cursor arrowCursor = new Cursor(this.getDisplay(),
					SWT.CURSOR_ARROW);
			fToolBar.setCursor(arrowCursor);
			fStopButton.setImage(stopImage);
			fStopButton.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					stopImage.dispose();
					arrowCursor.dispose();
				}
			});
			fStopButton.setEnabled(false);
			fStopButton.setToolTipText(JFaceResources
					.getString("ProgressMonitorPart.cancelToolTip")); //$NON-NLS-1$
		}
	}

	@Override
	public void internalWorked(double work) {
		fProgressIndicator.worked(work);
	}

	@Override
	public boolean isCanceled() {
		return fIsCanceled;
	}
