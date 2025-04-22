
	public static KeyStroke getKeystrokeOfBestActiveBindingFor(String commandId) {
		IBindingService bindingService = (IBindingService) PlatformUI
				.getWorkbench().getAdapter(IBindingService.class);
		TriggerSequence ts = bindingService.getBestActiveBindingFor(commandId);
		if (ts == null)
			return null;

		Trigger[] triggers = ts.getTriggers();
		if (triggers.length == 1 && triggers[0] instanceof KeyStroke)
			return (KeyStroke) triggers[0];
		else
			return null;
	}
