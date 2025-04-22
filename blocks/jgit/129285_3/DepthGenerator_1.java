					boolean failsDeepenSince = false;
					if (deepenSince != 0) {
						if ((p.flags & RevWalk.PARSED) == 0) {
							p.parseHeaders(walk);
						}
						failsDeepenSince =
							p.getCommitTime() < deepenSince;
					}

