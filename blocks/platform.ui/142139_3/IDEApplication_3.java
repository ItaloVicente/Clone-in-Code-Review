			boolean validWorkspace = checkValidWorkspace(shell, url);
			if (!validWorkspace) {
				throw new OperationCanceledException("Invalid workspace location: " + url); //$NON-NLS-1$
			}
			return url;
		} while (true);
