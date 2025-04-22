	private RequestValidator getRequestValidator() {
		switch (requestPolicy) {
			case ADVERTISED:
			default:
				return new AdvertisedRequestValidator();
			case REACHABLE_COMMIT:
				return new ReachableCommitRequestValidator();
			case TIP:
				return new TipRequestValidator();
			case REACHABLE_COMMIT_TIP:
				return new ReachableCommitTipRequestValidator();
			case ANY:
				return new AnyRequestValidator();
		}
	}

	private static class AdvertisedRequestValidator implements RequestValidator {
