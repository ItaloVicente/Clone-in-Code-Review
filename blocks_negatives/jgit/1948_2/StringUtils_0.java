				|| equalsIgnoreCase("off", stringValue)) {
			return false;

		} else {
			throw new IllegalArgumentException(MessageFormat.format(JGitText.get().notABoolean, stringValue));
		}
