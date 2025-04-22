			cmd.setHandler(HandlerServiceImpl.getHandler(CMD_ID, new IContextProvider() {

				@Override
				public IEclipseContext getContext() {
					return WorkbenchPlugin.getDefault().getWorkbenchContext();
				}
			}));
