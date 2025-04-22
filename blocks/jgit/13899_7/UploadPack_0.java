	private RequestPolicy getEffectiveRequestPolicy() {
		RequestPolicy rp;
		if (requestPolicy != null)
			rp = requestPolicy;
		else if (transferConfig.isAllowTipSha1InWant())
			rp = RequestPolicy.TIP;
		else
			rp = RequestPolicy.ADVERTISED;

		if (!biDirectionalPipe) {
			if (rp == RequestPolicy.ADVERTISED)
				rp = RequestPolicy.REACHABLE_COMMIT;
			else if (rp == RequestPolicy.TIP)
				rp = RequestPolicy.REACHABLE_COMMIT_TIP;
		}
		return rp;
	}

