				MCommand command = CommandsFactoryImpl.eINSTANCE.createCommand();
				command.setElementId(cmd.getId());
				command.setCategory(categories.get(cmd.getCategory().getId()));
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
