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
		public void checkWants(UploadPack up
				throws PackProtocolException
			if (!up.isBiDirectionalPipe())
				new ReachableCommitRequestValidator().checkWants(up
			else if (!wants.isEmpty())
				throw new PackProtocolException(MessageFormat.format(
						JGitText.get().wantNotValid
		}
	}

	private static class ReachableCommitRequestValidator
			implements RequestValidator {
		public void checkWants(UploadPack up
				throws PackProtocolException
			checkNotAdvertisedWants(up.getRevWalk()
					refIdSet(up.getAdvertisedRefs().values()));
		}
	}

	private static class TipRequestValidator implements RequestValidator {
		public void checkWants(UploadPack up
				throws PackProtocolException
			if (!up.isBiDirectionalPipe())
				new ReachableCommitTipRequestValidator().checkWants(up
			else if (!wants.isEmpty()) {
				Set<ObjectId> refIds =
					refIdSet(up.getRepository().getAllRefs().values());
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
		public void checkWants(UploadPack up
				throws PackProtocolException
			checkNotAdvertisedWants(up.getRevWalk()
					refIdSet(up.getRepository().getAllRefs().values()));
		}
	}

	private static class AnyRequestValidator implements RequestValidator {
		public void checkWants(UploadPack up
				throws PackProtocolException
		}
	}

	private static void checkNotAdvertisedWants(RevWalk walk
			List<RevObject> notAdvertisedWants
