				if (!repoHasBitmaps) {
					if (up.transferConfig.isAllowFilter()) {
						try (ObjectWalk objWalk = walk.toObjectWalkWithSameObjects()) {
							List<RevObject> havesAsObjs = objectIdsToRevObjects(
									objWalk, reachableFrom);
							ObjectReachabilityChecker reachabilityChecker = new PedestrianObjectReachabilityChecker(
									objWalk);
							Optional<RevObject> unreachable = reachabilityChecker
									.areAllReachable(wantsAsObjs,
											havesAsObjs.stream());
							if (unreachable.isPresent()) {
								throw new WantNotValidException(
										unreachable.get());
							}
						}
						return;
					}

