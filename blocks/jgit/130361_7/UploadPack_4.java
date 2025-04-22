
				DepthWalk.RevWalk dw = new DepthWalk.RevWalk(
						walk.getObjectReader()
				dw.setDeepenSince(req.getDeepenSince());
				dw.setDeepenNots(deepenNots);
				dw.assumeShallow(req.getClientShallowCommits());
				rw = dw;
