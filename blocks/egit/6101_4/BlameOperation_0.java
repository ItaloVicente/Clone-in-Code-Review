		BlameResult result;
		try {
			result = command.call();
		} catch (Exception e1) {
			Activator.error(e1.getMessage(), e1);
			return;
		}
