			if (!checkValidWorkspace(shell, instanceLoc.getURL())) {
				return EXIT_OK;
			}

			try {
				if (instanceLoc.lock()) {
					writeWorkspaceVersion();
					return null;
				}
