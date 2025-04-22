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
