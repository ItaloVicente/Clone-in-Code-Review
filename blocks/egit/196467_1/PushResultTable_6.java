	private String formatHookOutput(String hookOutput, String hookError) {
		String out = hookOutput.strip();
		String err = hookError.strip();
		if (out.isEmpty() && err.isEmpty()) {
			return ""; //$NON-NLS-1$
		}
		if (!out.isEmpty()) {
			out = prefixLines("stdout: ", out); //$NON-NLS-1$
		}
		if (!err.isEmpty()) {
			err = prefixLines("stderr: ", err); //$NON-NLS-1$
		}
		return MessageFormat.format(UIText.PushResultTable_PrePushHookOutput,
				out, err);
	}

	private String prefixLines(String prefix, String text) {
		return Stream.of(text.split("\n")) //$NON-NLS-1$
				.map(s -> prefix + s.stripTrailing())
				.collect(Collectors.joining("\n", "", "\n")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

