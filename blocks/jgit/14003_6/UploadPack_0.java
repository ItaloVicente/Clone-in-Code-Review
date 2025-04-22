		if (requestValidator instanceof AdvertisedRequestValidator)
			return RequestPolicy.ADVERTISED;
		if (requestValidator instanceof ReachableCommitRequestValidator)
			return RequestPolicy.REACHABLE_COMMIT;
		if (requestValidator instanceof TipRequestValidator)
			return RequestPolicy.TIP;
		if (requestValidator instanceof ReachableCommitTipRequestValidator)
			return RequestPolicy.REACHABLE_COMMIT_TIP;
		if (requestValidator instanceof AnyRequestValidator)
			return RequestPolicy.ANY;
		return null;
