			int commandTimeout = timeout;
			if (timeout > 0) {
				commandTimeout = checkTimeout(command
			}
			process = session.exec(command
			if (timeout > 0) {
				commandTimeout = checkTimeout(command
			}
