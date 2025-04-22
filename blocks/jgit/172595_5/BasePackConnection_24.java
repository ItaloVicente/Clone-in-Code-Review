	private void lsRefsImpl(Collection<RefSpec> refSpecs
			String... additionalPatterns) throws IOException {
		String agent = UserAgent.get();
		if (agent != null && isCapableOf(OPTION_AGENT)) {
			pckOut.writeString(OPTION_AGENT + '=' + agent);
		}
		pckOut.writeDelim();
		for (String refPrefix : getRefPrefixes(refSpecs
		}
		pckOut.end();
		final Map<String
		final Map<String
		for (;;) {
			String line = readLine();
			if (line == null) {
				break;
			}
			if (line.length() < 41 || line.charAt(40) != ' ') {
				throw invalidRefAdvertisementLine(line);
			}
			String name = line.substring(41
			final ObjectId id = toId(line
				additionalHaves.add(id);
