	private RequestPolicy getEffectiveRequestPolicy() {
		RequestPolicy rp;
		if (requestPolicy != null)
			rp = requestPolicy;
		else if (transferConfig.isAllowTipSha1InWant())
			rp = RequestPolicy.TIP;
		else
			rp = RequestPolicy.ADVERTISED;

		if (!biDirectionalPipe
				&& (rp == RequestPolicy.ADVERTISED || rp == RequestPolicy.TIP))
			rp = RequestPolicy.REACHABLE_COMMIT;
		return rp;
	}

