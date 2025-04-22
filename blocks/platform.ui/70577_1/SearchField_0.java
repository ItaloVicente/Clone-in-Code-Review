	private static final String QUICK_ACCESS_COMMAND_ID = "org.eclipse.ui.window.quickAccess"; //$NON-NLS-1$

	private String triggerSequenceFormat = null;
	protected String getQuickAccessTriggerSequenceFormat() {
		if (triggerSequenceFormat == null) {
			IBindingService bindingService = SearchField.this.window.getContext().get(IBindingService.class);
			for (Binding b : bindingService.getBindings()) {
				if (b.getParameterizedCommand() != null
						&& QUICK_ACCESS_COMMAND_ID.equalsIgnoreCase(b.getParameterizedCommand().getId())) {
					this.triggerSequenceFormat = b.getTriggerSequence().format();
					break;
				}
			}
		}
		return triggerSequenceFormat;
	}

