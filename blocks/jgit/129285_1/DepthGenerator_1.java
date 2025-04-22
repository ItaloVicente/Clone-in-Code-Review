					boolean failsShallowSince = false;
					if (shallowSince != 0) {
						if ((p.flags & RevWalk.PARSED) == 0) {
							p.parseHeaders(walk);
						}
						failsShallowSince =
							p.getCommitTime() < shallowSince;
					}

