		String[] cmdLineArgs = Platform.getCommandLineArgs();

		for (int i = 0; i < cmdLineArgs.length; i++) {
				String name = null;
				if (cmdLineArgs.length > i + 1) {
					name = cmdLineArgs[i + 1];
				}
					workspaceLocation = name;
				} else {
					workspaceLocation = Platform.getLocation().toOSString();
				}
				break;
			}
		}

