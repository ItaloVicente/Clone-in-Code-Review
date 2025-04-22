				if (!repoHasBitmaps) {
					if (up.transferConfig.isAllowFilter()) {
						try (ObjectWalk objWalk = walk.toObjectWalkWithSameObjects()) {
							checkReachabilityByWalkingObjects(objWalk,
									wantsAsObjs, reachableFrom);
						}
						return;
					}

