	/**
	 * Send the requested objects to the client.
	 *
	 * @param accumulator
	 *            where to write statistics about the content of the pack.
	 * @param req
	 *            request in process
	 * @param allTags
	 *            refs to search for annotated tags to include in the pack if
	 *            the {@link #OPTION_INCLUDE_TAG} capability was requested.
	 * @param unshallowCommits
	 *            shallow commits on the client that are now becoming unshallow
	 * @param deepenNots
	 *            objects that the client specified using --shallow-exclude
	 * @param pckOut
	 *            output writer
	 * @throws IOException
	 *             if an error occurred while generating or writing the pack.
	 */
	private void sendPack(PackStatistics.Accumulator accumulator,
			FetchRequest req,
			@Nullable Collection<Ref> allTags,
			List<ObjectId> unshallowCommits,
			List<ObjectId> deepenNots,
			PacketLineOut pckOut) throws IOException {
		Set<String> caps = req.getClientCapabilities();
		boolean sideband = caps.contains(OPTION_SIDE_BAND)
				|| caps.contains(OPTION_SIDE_BAND_64K);

		if (sideband) {
			errOut = new SideBandErrorWriter();

			int bufsz = SideBandOutputStream.SMALL_BUF;
			if (req.getClientCapabilities().contains(OPTION_SIDE_BAND_64K)) {
				bufsz = SideBandOutputStream.MAX_BUF;
			}
			OutputStream packOut = new SideBandOutputStream(
					SideBandOutputStream.CH_DATA, bufsz, rawOut);

			ProgressMonitor pm = NullProgressMonitor.INSTANCE;
			if (!req.getClientCapabilities().contains(OPTION_NO_PROGRESS)) {
				msgOut = new SideBandOutputStream(
						SideBandOutputStream.CH_PROGRESS, bufsz, rawOut);
				pm = new SideBandProgressMonitor(msgOut);
			}

			sendPack(pm, pckOut, packOut, req, accumulator, allTags,
					unshallowCommits, deepenNots);
			pckOut.end();
		} else {
			sendPack(NullProgressMonitor.INSTANCE, pckOut, rawOut, req,
					accumulator, allTags, unshallowCommits, deepenNots);
		}
	}

	/**
	 * Send the requested objects to the client.
	 *
	 * @param pm
	 *            progress monitor
	 * @param pckOut
	 *            PacketLineOut that shares the output with packOut
	 * @param packOut
	 *            packfile output
	 * @param req
	 *            request being processed
	 * @param accumulator
	 *            where to write statistics about the content of the pack.
	 * @param allTags
	 *            refs to search for annotated tags to include in the pack if
	 *            the {@link #OPTION_INCLUDE_TAG} capability was requested.
	 * @param unshallowCommits
	 *            shallow commits on the client that are now becoming unshallow
	 * @param deepenNots
	 *            objects that the client specified using --shallow-exclude
	 * @throws IOException
	 *             if an error occurred while generating or writing the pack.
	 */
	private void sendPack(ProgressMonitor pm, PacketLineOut pckOut,
			OutputStream packOut, FetchRequest req,
			PackStatistics.Accumulator accumulator,
			@Nullable Collection<Ref> allTags, List<ObjectId> unshallowCommits,
			List<ObjectId> deepenNots) throws IOException {
		if (wantAll.isEmpty()) {
			preUploadHook.onSendPack(this, wantIds, commonBase);
		} else {
			preUploadHook.onSendPack(this, wantAll, commonBase);
		}
		msgOut.flush();

		advertised = null;
		refs = null;

		PackConfig cfg = packConfig;
		if (cfg == null)
			cfg = new PackConfig(db);
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

			if (wantAll.isEmpty()) {
				pw.preparePack(pm, wantIds, commonBase,
						req.getClientShallowCommits());
			} else {
				walk.reset();

				ObjectWalk ow = rw.toObjectWalkWithSameObjects();
				pw.preparePack(pm, ow, wantAll, commonBase, PackWriter.NONE);
				rw = ow;
			}

			if (req.getClientCapabilities().contains(OPTION_INCLUDE_TAG)
					&& allTags != null) {
				for (Ref ref : allTags) {
					ObjectId objectId = ref.getObjectId();
					if (objectId == null) {
						continue;
					}

					if (wantAll.isEmpty()) {
						if (wantIds.contains(objectId))
							continue;
					} else {
						RevObject obj = rw.lookupOrNull(objectId);
						if (obj != null && obj.has(WANT))
							continue;
					}

					if (!ref.isPeeled())
						ref = db.getRefDatabase().peel(ref);

					ObjectId peeledId = ref.getPeeledObjectId();
					objectId = ref.getObjectId();
					if (peeledId == null || objectId == null)
						continue;

					objectId = ref.getObjectId();
					if (pw.willInclude(peeledId) && !pw.willInclude(objectId)) {
						RevObject o = rw.parseAny(objectId);
						addTagChain(o, pw);
						pw.addObject(o);
					}
				}
			}

			if (pckOut.isUsingSideband()) {
				if (req instanceof FetchV2Request &&
						cachedPackUriProvider != null &&
						!((FetchV2Request) req).getPackfileUriProtocols().isEmpty()) {
					FetchV2Request reqV2 = (FetchV2Request) req;
					pw.setPackfileUriConfig(new PackWriter.PackfileUriConfig(
							pckOut,
							reqV2.getPackfileUriProtocols(),
							cachedPackUriProvider));
				} else {
				}
			}
			pw.writePack(pm, NullProgressMonitor.INSTANCE, packOut);

			if (msgOut != NullOutputStream.INSTANCE) {
				String msg = pw.getStatistics().getMessage() + '\n';
				msgOut.write(Constants.encode(msg));
				msgOut.flush();
			}

		} finally {
			statistics = pw.getStatistics();
			if (statistics != null) {
				postUploadHook.onPostUpload(statistics);
			}
			pw.close();
		}
	}

