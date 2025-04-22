			try {
				readPackHeader();

				entries = new PackedObjectInfo[(int) objectCount];
				baseById = new ObjectIdSubclassMap<DeltaChain>();
				baseByPos = new LongMap<UnresolvedDelta>();
				deferredCheckBlobs = new ArrayList<PackedObjectInfo>();

				progress.beginTask(JGitText.get().receivingObjects,
						(int) objectCount);
				for (int done = 0; done < objectCount; done++) {
					indexOneObject();
					progress.update(1);
					if (progress.isCancelled())
						throw new IOException(JGitText.get().downloadCancelled);
				}
				readPackFooter();
				endInput();
				if (!deferredCheckBlobs.isEmpty())
					doDeferredCheckBlobs();
				progress.endTask();
				if (deltaCount > 0) {
					if (packOut == null)
						throw new IOException(JGitText.get().needPackOut);
					resolveDeltas(progress);
					if (entryCount < objectCount) {
						if (!fixThin) {
							throw new IOException(MessageFormat.format(
									JGitText.get().packHasUnresolvedDeltas, (objectCount - entryCount)));
						}
						fixThinPack(progress);
