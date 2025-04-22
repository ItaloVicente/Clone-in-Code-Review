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
		this.quickAccessTriggerSequenceFormat = triggerSequence != null ? triggerSequence.format() : null;
	}

