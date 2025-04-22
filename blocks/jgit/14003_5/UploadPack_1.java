		switch (policy) {
			case ADVERTISED:
			default:
				requestValidator = new AdvertisedRequestValidator();
				break;
			case REACHABLE_COMMIT:
				requestValidator = new ReachableCommitRequestValidator();
				break;
			case TIP:
				requestValidator = new TipRequestValidator();
				break;
			case REACHABLE_COMMIT_TIP:
				requestValidator = new ReachableCommitTipRequestValidator();
				break;
			case ANY:
				requestValidator = new AnyRequestValidator();
				break;
		}
	}

	public void setRequestValidator(RequestValidator validator) {
		requestValidator = validator != null ? validator
				: new AdvertisedRequestValidator();
