			Control control = getControl();
			if (control == null || control.isDisposed()) {
				if (logWhenDisposed) {
					String message = "Ignored labelProviderChanged notification because control is diposed." + //$NON-NLS-1$
							" This indicates a potential memory leak."; //$NON-NLS-1$
					if (!InternalPolicy.DEBUG_LOG_LABEL_PROVIDER_NOTIFICATIONS_WHEN_DISPOSED) {
						logWhenDisposed = false;
						message += " This is only logged once per viewer instance," + //$NON-NLS-1$
								" but similar calls will still be ignored."; //$NON-NLS-1$
					}
					Policy.getLog().log(new Status(IStatus.WARNING, Policy.JFACE, message, new RuntimeException()));
				}
				return;
			}
			ContentViewer.this.handleLabelProviderChanged(event);
		}
	};
