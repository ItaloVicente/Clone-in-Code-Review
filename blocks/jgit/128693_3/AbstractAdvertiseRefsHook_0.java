		Repository r = receivePack.getRepository();
		RevWalk rw = receivePack.getRevWalk();

		Map<String
		Set<ObjectId> haves = getAdvertisedHaves(r

		Collection<Ref> refs = refMap == null ? null : refMap.values();
		try {
			receivePack.setAdvertisedRefs(refs
		} catch (ServiceMayNotContinueException e) {
			throw e;
		} catch (IOException e) {
			throw new ServiceMayNotContinueException(
					JGitText.get().internalServerError
					e
		}
