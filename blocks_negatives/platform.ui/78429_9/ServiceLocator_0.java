
			if (servicesToDispose.size() > 0) {
				WorkbenchPlugin.log(StatusUtil.newStatus(IStatus.WARNING,
						String.format(
								"Services: %s register themselves while disposing (skipping dispose of such services).", //$NON-NLS-1$
								servicesToDispose),
						null));
			}
