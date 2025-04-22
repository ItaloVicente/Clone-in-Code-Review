		final PackWriter pw = new PackWriter(cfg, walk.getObjectReader(),
				accumulator);
		try {
			pw.setIndexDisabled(true);
			if (req.getFilterSpec().isNoOp()) {
				pw.setUseCachedPacks(true);
			} else {
				pw.setFilterSpec(req.getFilterSpec());
				pw.setUseCachedPacks(false);
			}
			pw.setUseBitmaps(
					req.getDepth() == 0
							&& req.getClientShallowCommits().isEmpty()
							&& req.getFilterSpec().getTreeDepthLimit() == -1);
			pw.setClientShallowCommits(req.getClientShallowCommits());
			pw.setReuseDeltaCommits(true);
			pw.setDeltaBaseAsOffset(
					req.getClientCapabilities().contains(OPTION_OFS_DELTA));
			pw.setThin(req.getClientCapabilities().contains(OPTION_THIN_PACK));
			pw.setReuseValidatingObjects(false);

			if (commonBase.isEmpty() && refs != null) {
				Set<ObjectId> tagTargets = new HashSet<>();
				for (Ref ref : refs.values()) {
					if (ref.getPeeledObjectId() != null)
						tagTargets.add(ref.getPeeledObjectId());
					else if (ref.getObjectId() == null)
						continue;
					else if (ref.getName().startsWith(Constants.R_HEADS))
						tagTargets.add(ref.getObjectId());
