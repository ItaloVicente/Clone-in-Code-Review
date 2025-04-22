	private void writePackedRefs(String content) throws IOException {
		File pr = new File(diskRepo.getDirectory(), "packed-refs");
		write(pr, content);
		FS fs = diskRepo.getFS();
		fs.setLastModified(pr.toPath(), Instant.now().minusSeconds(3600));
	}

