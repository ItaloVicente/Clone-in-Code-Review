		monitor.beginTask(remote(currentTask)
	}

	private static String remote(String msg) {
		String prefix = JGitText.get().prefixRemote;
		StringBuilder r = new StringBuilder(prefix.length() + msg.length() + 1);
		r.append(prefix);
		if (prefix.length() > 0 && prefix.charAt(prefix.length() - 1) != ' ') {
			r.append(' ');
		}
		r.append(msg);
		return r.toString();
