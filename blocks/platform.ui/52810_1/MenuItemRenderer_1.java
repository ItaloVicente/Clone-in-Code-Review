	protected String getToolTipText(MItem item) {
		String text = item.getLocalizedTooltip();
		if (item instanceof MHandledItem) {
			MHandledItem handledItem = (MHandledItem) item;
			IEclipseContext context = getContext(item);
			EBindingService bs = (EBindingService) context.get(EBindingService.class.getName());
			ParameterizedCommand cmd = handledItem.getWbCommand();
			if (cmd == null) {
				cmd = generateParameterizedCommand(handledItem, context);
			}
			TriggerSequence sequence = bs.getBestSequenceFor(handledItem.getWbCommand());
			if (sequence != null) {
				if (text == null) {
					try {
						text = cmd.getName();
					} catch (NotDefinedException e) {
						return null;
					}
				}
				text = text + " (" + sequence.format() + ')'; //$NON-NLS-1$
			}
			return text;
		}
		return text;
	}

