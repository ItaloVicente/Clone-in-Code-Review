	private void updateKeyBindingText() {
		updateQuickAccessTriggerSequenceFormat();
		updateText(txtQuickAcesss);
		txtQuickAcesss.requestLayout();
	}

	public String getQuickAccessTriggerSequenceFormat() {
		if (quickAccessTriggerSequenceFormat == null) {
			updateQuickAccessTriggerSequenceFormat();
		}
		return quickAccessTriggerSequenceFormat;
	}

	protected void updateQuickAccessTriggerSequenceFormat() {
		TriggerSequence triggerSequence = bindingService.getBestActiveBindingFor(QUICK_ACCESS_COMMAND_ID);
		if (triggerSequence == null) {
			for (Binding b : bindingService.getBindings()) {
				if (b.getParameterizedCommand() != null
						&& QUICK_ACCESS_COMMAND_ID.equalsIgnoreCase(b.getParameterizedCommand().getId())) {
					triggerSequence = b.getTriggerSequence();
				}
			}
		}
		this.quickAccessTriggerSequenceFormat = triggerSequence.format();
	}

