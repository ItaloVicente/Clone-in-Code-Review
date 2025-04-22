			if (result == ReturnCode.VALID) {
				try {
					if (instanceLoc.lock()) {
						writeWorkspaceVersion();
						return null;
					}
