			commands = new EObjectContainmentEList<MCommand>(MCommand.class, this,
					ApplicationPackageImpl.APPLICATION__COMMANDS) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void didChange() {
					elementIdToCommandMap = null;
					super.didChange();
				}
			};
