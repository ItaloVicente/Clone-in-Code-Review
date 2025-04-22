			if (isRefLogDisabled(cmd)) {
				continue;
			}

			String msg = getRefLogMessage(cmd);
			if (isRefLogIncludingResult(cmd)) {
