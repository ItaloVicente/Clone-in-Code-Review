		Matcher matcher = p.matcher(msg);
		while (matcher.find()) {
			styles.add(new StyleRange(h0 + matcher.start(), matcher.end()
					- matcher.start(), null, null, SWT.ITALIC));
		}

