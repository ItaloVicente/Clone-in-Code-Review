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
		public void checkWants(Repository db
				Set<ObjectId> advertised
				boolean biDirectionalPipe) throws PackProtocolException
			if (biDirectionalPipe)
				new ReachableCommitRequestValidator().checkWants(
						db
			else if (!wants.isEmpty())
				throw new PackProtocolException(MessageFormat.format(
						JGitText.get().wantNotValid
		}
	}

	private static class ReachableCommitRequestValidator
			implements RequestValidator {
		public void checkWants(Repository db
				Set<ObjectId> advertised
				boolean biDirectionalPipe) throws PackProtocolException
			checkNotAdvertisedWants(walk
		}
	}

	private static class TipRequestValidator implements RequestValidator {
		public void checkWants(Repository db
				Set<ObjectId> advertised
				boolean biDirectionalPipe) throws PackProtocolException
			if (biDirectionalPipe) {
				new ReachableCommitTipRequestValidator().checkWants(
						db
				return;
			}
			if (!wants.isEmpty()) {
				Set<ObjectId> refIds = refIdSet(db.getAllRefs().values());
				for (RevObject obj : wants) {
					if (!refIds.contains(obj))
						throw new PackProtocolException(MessageFormat.format(
								JGitText.get().wantNotValid
				}
			}
		}
	}

	private static class ReachableCommitTipRequestValidator
			implements RequestValidator {
		public void checkWants(Repository db
				Set<ObjectId> advertised
				boolean biDirectionalPipe) throws PackProtocolException
			checkNotAdvertisedWants(walk
		}
	}

	private static class AnyRequestValidator implements RequestValidator {
		public void checkWants(Repository db
				Set<ObjectId> advertised
				boolean biDirectionalPipe) throws PackProtocolException
		}
	}

	private static void checkNotAdvertisedWants(RevWalk walk
			List<RevObject> notAdvertisedWants
