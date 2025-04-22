
		if (e4Context == null) {
			WorkbenchPlugin.log(new IllegalStateException(
					"IWorkbenchSite.getShell() was called after part disposal: " + toString())); //$NON-NLS-1$
			return PlatformUI.getWorkbench().getDisplay().getActiveShell();
		}

