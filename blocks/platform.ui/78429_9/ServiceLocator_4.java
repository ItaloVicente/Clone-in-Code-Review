		if (isDisposed()) {
			IllegalStateException ex = new IllegalStateException("An attempt was made to register service " + service //$NON-NLS-1$
					+ " with implementation class " + api + " on a disposed service locator"); //$NON-NLS-1$//$NON-NLS-2$
			WorkbenchPlugin.log(StatusUtil.newStatus(IStatus.ERROR, ex.getMessage(), ex));
			return;
		}
