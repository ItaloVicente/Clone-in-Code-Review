		public void sendPack(PackStatistics.Accumulator accumulator)
				throws IOException {
			if (wantAll.isEmpty()) {
				preUploadHook.onSendPack(UploadPack.this
			} else {
				preUploadHook.onSendPack(UploadPack.this
			}
			msgOut.flush();

			PackConfig cfg = packConfig;
			if (cfg == null)
				cfg = new PackConfig(db);
			@SuppressWarnings("resource")
			final PackWriter pw = new PackWriter(cfg
					accumulator);
			try {
				configurePackWriter(pw);

				if (commonBase.isEmpty() && refs != null) {
					Set<ObjectId> tagTargets = new HashSet<>();
					for (Ref ref : refs.values()) {
						if (ref.getPeeledObjectId() != null)
							tagTargets.add(ref.getPeeledObjectId());
						else if (ref.getObjectId() == null) {
							continue;
						} else if (ref.getName().startsWith(Constants.R_HEADS)) {
							tagTargets.add(ref.getObjectId());
						}
					}
					pw.setTagTargets(tagTargets);
				}

				preparePack(pw
				writePack(pw);

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

		protected void configurePackWriter(PackWriter pw) {
