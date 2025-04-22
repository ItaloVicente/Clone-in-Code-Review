		if (!remapAll()) {
			try {
				store();
			} catch (CoreException e) {
				IStatus status = e.getStatus();
				Activator.logError(status.getMessage(), status.getException());
			}
		}
