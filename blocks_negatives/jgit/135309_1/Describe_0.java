			try {
				result = cmd.call();
			} catch (RefNotFoundException e) {
				throw die(CLIText.get().noNamesFound, e);
			}
			if (result == null)
