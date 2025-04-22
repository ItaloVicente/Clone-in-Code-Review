			boolean validWorkspace = checkValidWorkspace(shell, url);
			if (!validWorkspace) {
				throw new OperationCanceledException();
			}
			return url;
		} while (true);
