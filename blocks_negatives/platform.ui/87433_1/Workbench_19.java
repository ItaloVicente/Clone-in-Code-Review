				new CommandCallback(bindingManager, commandManager, new IActiveChecker() {
					@Override
					public final boolean isActive(final String commandId) {
						return workbenchActivitySupport.getActivityManager().getIdentifier(
								commandId).isEnabled();
					}
				}, new IExecuteApplicable() {
					@Override
					public boolean isApplicable(IAction action) {
						return !(action instanceof CommandAction);
					}
				}));
