	protected Map<String, IAdaptable> getHyperlinkTargets() {
		return Collections.singletonMap("org.eclipse.ui.DefaultTextEditor", //$NON-NLS-1$
				getDefaultTarget());
	}

	protected IAdaptable getDefaultTarget() {
		return null;
	}

