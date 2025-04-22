					try {
						IParameter parm = cmd
								.getParameter(HistoryViewCommands.COMPARE_MODE_PARAM);
						parms = new Parameterization[] { new Parameterization(
								parm, Boolean.TRUE.toString()) };
					} catch (NotDefinedException e) {
						Activator.handleError(e.getMessage(), e, true);
						parms = null;
					}
				} else
					parms = null;
				ParameterizedCommand pcmd = new ParameterizedCommand(cmd, parms);
				try {
					hsrv.executeCommandInContext(pcmd, null, hsrv
							.getCurrentState());
				} catch (Exception e) {
					Activator.handleError(e.getMessage(), e, true);
