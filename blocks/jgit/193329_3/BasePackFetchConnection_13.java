	private void sendShallow(PacketLineOut output) throws IOException {
		if (depth != null) {
			output.writeString("deepen " + depth);
		}

		if (deepenSince != null) {
			output.writeString("deepen-since " + deepenSince.getEpochSecond());
		}

		if (deepenNots != null) {
			for (String deepenNotRef : deepenNots) {
				output.writeString("deepen-not " + deepenNotRef);
			}
		}

		for (ObjectId shallowCommit : local.getObjectDatabase().getShallowCommits()) {
			output.writeString("shallow " + shallowCommit.name());
		}
	}

	private void handleShallowUnshallow(PacketLineIn input) throws IOException {
		String line;
		ObjectDatabase objectDatabase = local.getObjectDatabase();
		HashSet<ObjectId> shallowCommits = new HashSet<>(objectDatabase.getShallowCommits());
		while (not(PacketLineIn::isDelimiter).and(not(PacketLineIn::isEnd)).test(line = input.readString())) {
			if (line.startsWith("shallow ")) {
				shallowCommits.add(ObjectId.fromString(line.substring("shallow ".length())));
			} else if (line.startsWith("unshallow ")) {
				shallowCommits.remove(ObjectId.fromString(line.substring("unshallow ".length())));
			}
		}
		objectDatabase.setShallowCommits(shallowCommits);
	}

