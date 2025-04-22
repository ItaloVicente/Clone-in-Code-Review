			MergeResult mres = res.getMergeResult();
			if (mres != null) {
				switch (mres.getMergeStatus()) {
				case ALREADY_UP_TO_DATE:
				case FAST_FORWARD:
				case MERGED:
					break;
				default:
					error = true;
					break;
				}
			}
			RebaseResult rres = res.getRebaseResult();
			if (rres != null) {
				switch (rres.getStatus()) {
				case ABORTED:
				case FAILED:
				case STOPPED:
					break;
				default:
					error = true;
					break;
				}
			}
			if (error)
