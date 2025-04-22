		graphDetailSplit.setLayoutData(gd);

		graph = new CommitGraphTable(graphDetailSplit);
		graph.getTableView().addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				if (!input.isSingleFile()) {
					return;
				}

				ICommandService srv = (ICommandService) getSite().getService(
						ICommandService.class);
				IHandlerService hsrv = (IHandlerService) getSite().getService(
						IHandlerService.class);
				Command cmd = srv.getCommand(HistoryViewCommands.SHOWVERSIONS);
				Parameterization[] parms;
				if (store
						.getBoolean(UIPreferences.RESOURCEHISTORY_COMPARE_MODE)) {
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
				}
			}
		});
