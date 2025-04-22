	protected void lsRefs(Collection<RefSpec> refSpecs
			String... additionalPatterns) throws TransportException {
		try {
			lsRefsImpl(refSpecs
		} catch (TransportException err) {
			close();
			throw err;
		} catch (IOException | RuntimeException err) {
			close();
			throw new TransportException(err.getMessage()
		}
	}

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
