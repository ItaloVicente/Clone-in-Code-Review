					if (up.transferConfig.isAllowFilter()) {
						closeWalk = false;
						checkReachabilityByWalkingObjects(walk
								reachableFrom);
						return;
					}

