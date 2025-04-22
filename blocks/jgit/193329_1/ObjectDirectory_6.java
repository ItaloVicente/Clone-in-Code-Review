	@Override
	public void setShallowCommits(Set<ObjectId> shallowCommits) throws IOException {
		this.shallowCommitsIds = shallowCommits;
		try (BufferedWriter writer = Files.newBufferedWriter(shallowFile.toPath()
			for (ObjectId shallowCommit : shallowCommits) {
				writer.write(shallowCommit.name());
				writer.newLine();
			}
		}

		shallowFileSnapshot = FileSnapshot.save(shallowFile);
	}

