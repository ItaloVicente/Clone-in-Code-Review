			if (rc != 0) {
				boolean execError = isCommandExecutionError(rc);
				if (checkExitCode || execError) {
					throw new ToolException(
							new String(result.getStderr().toByteArray())
							result
				}
