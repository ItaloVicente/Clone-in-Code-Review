			if (checkValidWorkspace(shell, instanceLoc.getURL())) {
				try {
					if (instanceLoc.lock()) {
						writeWorkspaceVersion();
						return null;
					}
