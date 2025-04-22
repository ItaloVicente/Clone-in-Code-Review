
		final IAction selectAll = createStandardAction(ActionFactory.SELECT_ALL);
		copy = createStandardAction(ActionFactory.COPY);

		table.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				copy.setEnabled(canDoCopy());
			}
		});

		getControl().addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				site.getActionBars().setGlobalActionHandler(
						ActionFactory.SELECT_ALL.getId(), null);
				site.getActionBars().setGlobalActionHandler(
						ActionFactory.COPY.getId(), null);
				site.getActionBars().updateActionBars();
			}

			public void focusGained(FocusEvent e) {
				site.getActionBars().setGlobalActionHandler(
						ActionFactory.SELECT_ALL.getId(), selectAll);
				site.getActionBars().setGlobalActionHandler(
						ActionFactory.COPY.getId(), copy);
				site.getActionBars().updateActionBars();
			}
		});

		getTableView().addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				if (input == null || !input.isSingleFile()) {
					return;
				}

				ICommandService srv = (ICommandService) site
						.getService(ICommandService.class);
				IHandlerService hsrv = (IHandlerService) site
						.getService(IHandlerService.class);
				Command cmd = srv.getCommand(HistoryViewCommands.SHOWVERSIONS);
				Parameterization[] parms;
				if (Activator.getDefault().getPreferenceStore().getBoolean(
						UIPreferences.RESOURCEHISTORY_COMPARE_MODE)) {
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

		Control c = getControl();
		c.setMenu(menuMgr.createContextMenu(c));
		c.addMenuDetectListener(menuListener = new MenuListener(menuMgr,
				getTableView(), site, copy));
