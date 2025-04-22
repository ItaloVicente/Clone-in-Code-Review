		if (AUTOMATED_MODE) {
			IllegalStateException e = new IllegalStateException("Error dialog is supposed to be shown now"); //$NON-NLS-1$
			MultiStatus ms = new MultiStatus("org.eclipse.jface", IStatus.ERROR, title + " : " + message, e); //$NON-NLS-1$ //$NON-NLS-2$
			if (status != null) {
				ms.add(status);
			}
			Policy.getLog().log(ms);
		}
