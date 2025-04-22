		viewer.getControl().addHelpListener(new HelpListener() {

			@Override
			public void helpRequested(HelpEvent e) {
				Object provider = getAdapter(IContextProvider.class);
				if (provider == null)
					return;

				IContext context = ((IContextProvider) provider)
						.getContext(viewer.getControl());
				PlatformUI.getWorkbench().getHelpSystem().displayHelp(context);
			}
