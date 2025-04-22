	private static class V0V2PackWriter {
		private Accumulator accumulator;

		private FetchRequest req;

		private OutputStream packOut;

		private PacketLineOut pckOut;

		private ProgressMonitor pm;

		private OutputStream msgOut;

		private boolean sideband;

		private RevWalk walk;

		private RevFlag WANT;

		private CachedPackUriProvider cachedPackUriProvider;

		private Map<String

		private Repository db;

		private PackStatistics statistics;

		private PackConfig cfg;

		public V0V2PackWriter(RevWalk walk
				FetchRequest req
				ResponseBufferedOutputStream rawOut
				OutputStream msgOut
				CachedPackUriProvider cachedPackUriProvider
				Map<String
			this.walk = walk;
			this.accumulator = accumulator;
			this.req = req;
			this.pckOut = pckOut;
			this.WANT = WANT;
			this.cachedPackUriProvider = cachedPackUriProvider;
			this.refs = refs;
			this.db = db;
			this.cfg = new PackConfig(db);
			this.msgOut = msgOut;
			this.sideband = isSideband(req);

			if (this.sideband) {
				int bufsz = SideBandOutputStream.SMALL_BUF;
				if (req.getClientCapabilities()
						.contains(OPTION_SIDE_BAND_64K)) {
					bufsz = SideBandOutputStream.MAX_BUF;
				}
				packOut = new SideBandOutputStream(SideBandOutputStream.CH_DATA
						bufsz

				this.pm = NullProgressMonitor.INSTANCE;
				if (!req.getClientCapabilities().contains(OPTION_NO_PROGRESS)) {
					this.msgOut = new SideBandOutputStream(
							SideBandOutputStream.CH_PROGRESS
					this.pm = new SideBandProgressMonitor(msgOut);
				}
			} else {
				this.packOut = rawOut;
				this.pm = NullProgressMonitor.INSTANCE;
			}
		}

		void setPackConfig(PackConfig packConfig) {
			this.cfg = packConfig;
		}

		PackStatistics getStatistics() {
			return statistics;
		}


		void sendPack(@Nullable Collection<Ref> allTags
				List<ObjectId> unshallowCommits
				List<ObjectId> deepenNots
				Set<RevObject> wantAll
				throws IOException {
			msgOut.flush();

			final PackWriter pw = new PackWriter(cfg
					accumulator);
			try {
				pw.setIndexDisabled(true);
				if (req.getFilterSpec().isNoOp()) {
					pw.setUseCachedPacks(true);
				} else {
					pw.setFilterSpec(req.getFilterSpec());
					pw.setUseCachedPacks(false);
				}
				pw.setUseBitmaps(req.getDepth() == 0
						&& req.getClientShallowCommits().isEmpty()
						&& req.getFilterSpec().getTreeDepthLimit() == -1);
				pw.setClientShallowCommits(req.getClientShallowCommits());
				pw.setReuseDeltaCommits(true);
				pw.setDeltaBaseAsOffset(
						req.getClientCapabilities().contains(OPTION_OFS_DELTA));
				pw.setThin(
						req.getClientCapabilities().contains(OPTION_THIN_PACK));
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
				if (req.getDepth() > 0 || req.getDeepenSince() != 0
						|| !deepenNots.isEmpty()) {
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

				if (wantAll.isEmpty()) {
					pw.preparePack(pm
							req.getClientShallowCommits());
				} else {
					walk.reset();

					ObjectWalk ow = rw.toObjectWalkWithSameObjects();
					pw.preparePack(pm
							PackWriter.NONE);
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
						if (pw.willInclude(peeledId)
								&& !pw.willInclude(objectId)) {
							RevObject o = rw.parseAny(objectId);
							addTagChain(o
							pw.addObject(o);
						}
					}
				}

				if (pckOut.isUsingSideband()) {
					if (req instanceof FetchV2Request
							&& cachedPackUriProvider != null
							&& !((FetchV2Request) req).getPackfileUriProtocols()
									.isEmpty()) {
						FetchV2Request reqV2 = (FetchV2Request) req;
						pw.setPackfileUriConfig(
								new PackWriter.PackfileUriConfig(pckOut
										reqV2.getPackfileUriProtocols()
										cachedPackUriProvider));
					} else {
					}
				}
				pw.writePack(pm

				if (msgOut != NullOutputStream.INSTANCE) {
					String msg = pw.getStatistics().getMessage() + '\n';
					msgOut.write(Constants.encode(msg));
					msgOut.flush();
				}

			} finally {
				this.statistics = pw.getStatistics();
				pw.close();
				if (sideband) {
					pckOut.end();
				}
			}
