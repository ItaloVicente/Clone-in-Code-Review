	private ICommandManagerListener cmListener;

	public static MCommand createCommand(Command cmd, final MCategory categoryModel)
			throws NotDefinedException {
		MCommand command = CommandsFactoryImpl.eINSTANCE.createCommand();
		command.setElementId(cmd.getId());
		command.setCategory(categoryModel);
		command.setCommandName(cmd.getName());
		command.setDescription(cmd.getDescription());

		IParameter[] cmdParms = cmd.getParameters();
		if (cmdParms != null) {
			for (IParameter cmdParm : cmdParms) {
				MCommandParameter parmModel = CommandsFactoryImpl.eINSTANCE
						.createCommandParameter();
				parmModel.setElementId(cmdParm.getId());
				parmModel.setName(cmdParm.getName());
				parmModel.setOptional(cmdParm.isOptional());
				ParameterType parmType = cmd.getParameterType(cmdParm.getId());
				if (parmType != null) {
					parmModel.setTypeId(parmType.getId());
				}
				command.getParameters().add(parmModel);
			}
		}
		return command;
	}

