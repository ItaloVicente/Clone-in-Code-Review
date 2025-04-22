			ReturnCode result = checkValidWorkspace(shell, url);
			if (result == ReturnCode.INVALID) {
				throw new OperationCanceledException("Invalid workspace location: " + url); //$NON-NLS-1$
			}
			if (result == ReturnCode.EXIT) {
				return null;
			}
			return url;
		} while (true);
