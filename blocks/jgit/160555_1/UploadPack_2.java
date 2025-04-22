							List<RevObject> havesAsObjs = objectIdsToRevObjects(
									objWalk
							PedestrianObjectReachabilityChecker reachabilityChecker = new PedestrianObjectReachabilityChecker(
									objWalk);
							Optional<RevObject> unreachable = reachabilityChecker
									.areAllReachable(wantsAsObjs
											havesAsObjs.stream());
							if (unreachable.isPresent()) {
								throw new WantNotValidException(
										unreachable.get());
							}
