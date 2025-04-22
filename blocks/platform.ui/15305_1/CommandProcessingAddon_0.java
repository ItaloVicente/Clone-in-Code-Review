	private ICommandManagerListener cmListener;

	public static MCommand createCommand(Command cmd, EModelService modelService,
			final MCategory categoryModel) throws NotDefinedException {
		MCommand command = modelService.createModelElement(MCommand.class);
		command.setElementId(cmd.getId());
		command.setCategory(categoryModel);
		command.setCommandName(cmd.getName());
		command.setDescription(cmd.getDescription());

		IParameter[] cmdParms = cmd.getParameters();
		if (cmdParms != null) {
			for (IParameter cmdParm : cmdParms) {
				MCommandParameter parmModel = modelService
						.createModelElement(MCommandParameter.class);
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

