	/**
	 * Sets the given help context computer on the given action.
	 * <p>
	 * Use this method when the help contexts cannot be computed in advance.
	 * Help contexts can either supplied as a static list, or calculated with a
	 * context computer (but not both).
	 * </p>
	 *
	 * @param action
	 *            the action on which to register the computer
	 * @param computer
	 *            the computer to determine the help contexts for the control
	 *            when F1 help is invoked
	 * @deprecated context computers are no longer supported, clients should
	 *             implement their own help listener
	 */
	@Deprecated
	public void setHelp(IAction action, final IContextComputer computer) {
		action.setHelpListener(event -> {
			Object[] helpContexts = computer.computeContexts(event);
			if (helpContexts != null && helpContexts.length > 0
					&& getHelpUI() != null) {
				IContext context = null;
				if (helpContexts[0] instanceof String) {
					context = HelpSystem
							.getContext((String) helpContexts[0]);
				} else if (helpContexts[0] instanceof IContext) {
					context = (IContext) helpContexts[0];
				}
				if (context != null) {
					Point point = computePopUpLocation(event.widget
							.getDisplay());
					displayContext(context, point.x, point.y);
				}
			}
		});
	}
