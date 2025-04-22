		int headerEnd = d.length();
		String msg = commit.getFullMessage().trim();
		Matcher spm = FOOTER_PATTERN.matcher(msg);
		int footerStart = -1;
		if (spm.find()) {
			if (fill) {
				String footer = msg.substring(spm.start());
				msg = msg.substring(0, spm.start());
				msg = msg.replaceAll("([\\w.,; \t])\n(\\w)", "$1 $2") //$NON-NLS-1$ //$NON-NLS-2$
						+ footer;
				footerStart = headerEnd + msg.length() - footer.length();
			} else {
				footerStart = headerEnd + spm.start();
