		String[] unitPrefix = new String[] { "seconds", "minutes", "hours", "days", "milliseconds" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		long[] prefixMultiplicator = new long[] { 1000, 60_000, 3_600_000, 1_314_000_000, 1 };
		assert unitPrefix.length == prefixMultiplicator.length;

		Pattern p = Pattern.compile("(\\d+)\\s*([a-z]*)", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
		Matcher m = p.matcher(durationStr.trim());
		if (m.matches()) {
			long value = Long.parseLong(m.group(1));
			String unit = m.group(2);
			if (unit.isEmpty()) {
				return value;
			}
			for (int i = 0; i < unitPrefix.length; i++) {
				if (unitPrefix[i].startsWith(unit)) {
					return value * prefixMultiplicator[i];
				}
			}
		}
		return -1;
	}

	protected long parseDuration(String durationStr, long defaultDuration) {
		long duration = parseDuration(durationStr);
		return duration >= 0 ? duration : defaultDuration;
