	/**
	 * Issue a protocol V2 ls-refs command and read its response.
	 *
	 * @param refSpecs
	 *            to produce ref prefixes from if the server supports git
	 *            protocol V2
	 * @param additionalPatterns
	 *            to use for ref prefixes if the server supports git protocol V2
	 * @throws TransportException
	 *             if the command could not be run or its output not be read
	 */
	protected void lsRefs(Collection<RefSpec> refSpecs,
			String... additionalPatterns) throws TransportException {
		try {
			lsRefsImpl(refSpecs, additionalPatterns);
		} catch (TransportException err) {
			close();
			throw err;
		} catch (IOException | RuntimeException err) {
			close();
			throw new TransportException(err.getMessage(), err);
		}
	}
