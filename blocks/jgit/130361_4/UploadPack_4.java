
				DepthWalk.RevWalk dw = new DepthWalk.RevWalk(
						walk.getObjectReader()
				dw.setDeepenSince(shallowSince);
				dw.setDeepenNots(deepenNots);
				dw.assumeShallow(clientShallowCommits);
				rw = dw;
