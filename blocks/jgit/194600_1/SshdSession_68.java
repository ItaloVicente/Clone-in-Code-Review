		} finally {
			if (authLog != null) {
				authLog.clear();
			}
		}
	}

	private String withAuthLog(String message
		if (authLog != null) {
			String log = String.join(System.lineSeparator()
			if (!log.isEmpty()) {
				return message + System.lineSeparator() + log;
			}
