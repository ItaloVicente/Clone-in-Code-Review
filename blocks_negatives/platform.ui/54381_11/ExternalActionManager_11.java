			this(bindingManager, commandManager, activeChecker,
					new IExecuteApplicable() {
				@Override
				public boolean isApplicable(IAction action) {
					return true;
				}
			});
