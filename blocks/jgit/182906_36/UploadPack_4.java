				RevWalk rw = walk;
				if (req.getDepth() > 0 || req.getDeepenSince() != 0 || !deepenNots.isEmpty()) {
					int walkDepth = req.getDepth() == 0 ? Integer.MAX_VALUE
							: req.getDepth() - 1;
					pw.setShallowPack(req.getDepth()

					DepthWalk.RevWalk dw = new DepthWalk.RevWalk(
							walk.getObjectReader()
					dw.setDeepenSince(req.getDeepenSince());
					dw.setDeepenNots(deepenNots);
					dw.assumeShallow(req.getClientShallowCommits());
					rw = dw;
				}
