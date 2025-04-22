			try {
				if (instanceLoc.lock()) {
					writeWorkspaceVersion();
					return null;
				}

				File workspaceDirectory = new File(instanceLoc.getURL().getFile());
				if (workspaceDirectory.exists()) {
					if (isDevLaunchMode(applicationArguments)) {
						return EXIT_WORKSPACE_LOCKED;
