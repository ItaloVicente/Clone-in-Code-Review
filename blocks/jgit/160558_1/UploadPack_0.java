				try (ObjectWalk objWalk = walk.toObjectWalkWithSameObjects()) {
					List<RevObject> havesAsObjs = objectIdsToRevObjects(objWalk
							reachableFrom);
					ObjectReachabilityChecker reachabilityChecker = new BitmappedObjectReachabilityChecker(
							objWalk);
					Optional<RevObject> unreachable = reachabilityChecker
							.areAllReachable(wantsAsObjs
					if (unreachable.isPresent()) {
						throw new WantNotValidException(unreachable.get());
					}
				}
