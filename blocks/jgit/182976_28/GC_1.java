	public void writeCommitGraph() throws IOException {
		Collection<Ref> refsBefore = getAllRefs();
		Set<ObjectId> wants = new HashSet<>();

		for (Ref ref : refsBefore) {
			checkCancelled();
			if (ref.getPeeledObjectId() != null) {
				wants.add(ref.getPeeledObjectId());
				continue;
			}

			if (ref.getObjectId() != null) {
				wants.add(ref.getObjectId());
			}
		}
		writeCommitGraph(wants);
	}

	public void writeCommitGraph(@NonNull Set<? extends ObjectId> wants)
			throws IOException {
		checkCancelled();
		if (wants.isEmpty()) {
			return;
		}
		File tmpFile = null;
		try (RevWalk walk = new RevWalk(repo)) {
			CommitGraphWriter writer = new CommitGraphWriter(
					GraphCommits.fromWalk(pm
			tmpFile = File.createTempFile("commit_"
					repo.getObjectDatabase().getInfoDirectory());
			try (FileOutputStream fos = new FileOutputStream(tmpFile);
					FileChannel channel = fos.getChannel();
					OutputStream channelStream = Channels
							.newOutputStream(channel)) {
				writer.write(pm
				channel.force(true);
			}

			File realFile = new File(repo.getObjectsDirectory()
					Constants.INFO_COMMIT_GRAPH);
			FileUtils.rename(tmpFile
		} finally {
			if (tmpFile != null && tmpFile.exists()) {
				tmpFile.delete();
			}
		}
		deleteTempCommitGraph();
	}

	private void deleteTempCommitGraph() {
		Path objectsDir = repo.getObjectDatabase().getInfoDirectory().toPath();
		Instant threshold = Instant.now().minus(1
		if (!Files.exists(objectsDir)) {
			return;
		}
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(objectsDir
			stream.forEach(t -> {
				try {
					Instant lastModified = Files.getLastModifiedTime(t)
							.toInstant();
					if (lastModified.isBefore(threshold)) {
						Files.deleteIfExists(t);
					}
				} catch (IOException e) {
					LOG.error(e.getMessage()
				}
			});
		} catch (IOException e) {
			LOG.error(e.getMessage()
		}
	}

	boolean willWriteCommitGraph() {
		return repo.getConfig().getBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_WRITE_COMMIT_GRAPH
				DEFAULT_WRITE_COMMIT_GRAPH);
	}

