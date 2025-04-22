			return;
		}
		matcher = FULL_URI.matcher(s);
		if (matcher.matches()) {
			scheme = matcher.group(1);
			user = matcher.group(2);
			pass = matcher.group(3);
			host = matcher.group(4);
			if (matcher.group(5) != null)
				port = Integer.parseInt(matcher.group(5));
			path = cleanLeadingSlashes(
					n2e(matcher.group(6)) + n2e(matcher.group(7))
			return;
		}
		matcher = RELATIVE_SCP_URI.matcher(s);
		if (matcher.matches()) {
			user = matcher.group(1);
			pass = matcher.group(2);
			host = matcher.group(3);
			path = matcher.group(4);
			return;
		}
		matcher = ABSOLUTE_SCP_URI.matcher(s);
		if (matcher.matches()) {
			user = matcher.group(1);
			pass = matcher.group(2);
			host = matcher.group(3);
			path = matcher.group(4);
			return;
		}
		matcher = LOCAL_FILE.matcher(s);
		if (matcher.matches()) {
			path = matcher.group(1);
			return;
