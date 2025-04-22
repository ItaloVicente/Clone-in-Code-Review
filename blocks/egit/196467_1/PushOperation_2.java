	private void addHookMessage(URIish uri, String msg, StringBuilder all) {
		if (!msg.isEmpty()) {
			if (all.length() > 0 && all.charAt(all.length() - 1) != '\n') {
				all.append('\n');
			}
			all.append(
					MessageFormat.format(CoreText.PushOperation_ForUri, uri));
			all.append('\n');
			all.append(msg);
		}
	}

