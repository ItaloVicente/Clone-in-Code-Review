	private void setResult(final RefUpdate.Result r) {
		switch (r) {
		case NOT_ATTEMPTED:
			setResult(Result.NOT_ATTEMPTED);
			break;

		case LOCK_FAILURE:
		case IO_FAILURE:
			setResult(Result.LOCK_FAILURE);
			break;

		case NO_CHANGE:
		case NEW:
		case FORCED:
		case FAST_FORWARD:
			setResult(Result.OK);
			break;

		case REJECTED:
			setResult(Result.REJECTED_NONFASTFORWARD);
			break;

		case REJECTED_CURRENT_BRANCH:
			setResult(Result.REJECTED_CURRENT_BRANCH);
			break;

		default:
			setResult(Result.REJECTED_OTHER_REASON
			break;
		}
	}

