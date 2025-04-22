
	public static KeyStroke getKeystrokeOfBestActiveBindingFor(String commandId) {
		KeyStroke stroke = null;
		IBindingService bindingService = (IBindingService) PlatformUI
				.getWorkbench().getAdapter(IBindingService.class);
		TriggerSequence ts = bindingService
				.getBestActiveBindingFor("org.eclipse.ui.edit.text.contentAssist.proposals"); //$NON-NLS-1$
		if (ts == null)
			return null;

		Trigger[] triggers = ts.getTriggers();
		for (Trigger t : triggers) {
			if (t instanceof KeyStroke)
				stroke = (KeyStroke) t;
		}

		return stroke;
	}
