					String property = System.getProperty(PROP_VM);
					if (property == null) {
						if (!Platform.inDevelopmentMode()) {
							WorkbenchPlugin.log(NLS.bind(WorkbenchMessages.Workbench_missingPropertyMessage, PROP_VM));
						}
					} else {
						String command_line = buildCommandLine(uri.toString());
						if (command_line != null) {
							System.setProperty(PROP_EXIT_CODE, IApplication.EXIT_RELAUNCH.toString());
							System.setProperty(IApplicationContext.EXIT_DATA_PROPERTY, command_line);
						}
