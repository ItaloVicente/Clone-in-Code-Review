
				ICommandService srv = (ICommandService) getSite().getService(
						ICommandService.class);
				IHandlerService hsrv = (IHandlerService) getSite().getService(
						IHandlerService.class);
				Command cmd = srv.getCommand(HistoryViewCommands.SHOWVERSIONS);
				Parameterization[] parms;
