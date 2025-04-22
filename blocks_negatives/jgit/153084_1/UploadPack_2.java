			if (commonBase.isEmpty() && refs != null) {
				Set<ObjectId> tagTargets = new HashSet<>();
				for (Ref ref : refs.values()) {
					if (ref.getPeeledObjectId() != null)
						tagTargets.add(ref.getPeeledObjectId());
					else if (ref.getObjectId() == null)
						continue;
					else if (ref.getName().startsWith(Constants.R_HEADS))
						tagTargets.add(ref.getObjectId());
				}
				pw.setTagTargets(tagTargets);
			}

			RevWalk rw = walk;
			if (req.getDepth() > 0 || req.getDeepenSince() != 0 || !deepenNots.isEmpty()) {
				int walkDepth = req.getDepth() == 0 ? Integer.MAX_VALUE
						: req.getDepth() - 1;
				pw.setShallowPack(req.getDepth(), unshallowCommits);

				DepthWalk.RevWalk dw = new DepthWalk.RevWalk(
						walk.getObjectReader(), walkDepth);
				dw.setDeepenSince(req.getDeepenSince());
				dw.setDeepenNots(deepenNots);
				dw.assumeShallow(req.getClientShallowCommits());
				rw = dw;
			}

