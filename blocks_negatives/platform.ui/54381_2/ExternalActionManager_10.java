			this(bindingManager, commandManager, new IActiveChecker() {
				@Override
				public boolean isActive(String commandId) {
					return true;
				}

			}, new IExecuteApplicable() {
				@Override
				public boolean isApplicable(IAction action) {
					return true;
				}
			});
