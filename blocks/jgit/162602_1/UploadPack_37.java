				try (ObjectWalk objWalk = walk.toObjectWalkWithSameObjects()) {
					Stream<RevObject> startersAsObjs = importantRefsFirst(visibleRefs)
							.map(UploadPack::refToObjectId)
							.map(objId -> objectIdToRevObject(objWalk

					ObjectReachabilityChecker reachabilityChecker = objWalk
							.createObjectReachabilityChecker();
					Optional<RevObject> unreachable = reachabilityChecker
							.areAllReachable(wantsAsObjs
					if (unreachable.isPresent()) {
						throw new WantNotValidException(unreachable.get());
					}
