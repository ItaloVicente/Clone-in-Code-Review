	private void lsRefsImpl(Collection<RefSpec> refSpecs,
			String... additionalPatterns) throws IOException {
		TemporaryBuffer state = null;
		PacketLineOut pckState = null;
		PacketLineOut output = pckOut;
		if (statelessRPC) {
			state = new TemporaryBuffer.Heap(Integer.MAX_VALUE);
			pckState = new PacketLineOut(state);
			output = pckState;
		}
		String agent = UserAgent.get();
		if (agent != null && isCapableOf(OPTION_AGENT)) {
			output.writeString(OPTION_AGENT + '=' + agent);
		}
		output.writeDelim();
		for (String refPrefix : getRefPrefixes(refSpecs, additionalPatterns)) {
		}
		output.end();
		output.flush();
		if (state != null) {
			state.writeTo(out, null);
			out.flush();
			state = null;
			pckState = null;
		}
		final LinkedHashMap<String, Ref> avail = new LinkedHashMap<>();
		for (;;) {
			String line = readLine();
			if (line == null) {
				break;
			}
			if (line.length() < 41 || line.charAt(40) != ' ') {
				throw invalidRefAdvertisementLine(line);
			}
			String name = line.substring(41, line.length());
			final ObjectId id = toId(line, line.substring(0, 40));
				additionalHaves.add(id);
