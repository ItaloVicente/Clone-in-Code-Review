	@Override
	protected Map<KexProposalOption, String> setNegotiationResult(
			Map<KexProposalOption, String> guess) {
		Map<KexProposalOption, String> result = super.setNegotiationResult(
				guess);
		if (log.isDebugEnabled()) {
			result.forEach((option, value) -> log.debug(
					"setNegotiationResult({}) Kex: {} = {}", this, //$NON-NLS-1$
					option.getDescription(), value));
		}
		return result;
	}

