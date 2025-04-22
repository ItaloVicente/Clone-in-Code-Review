	private void sendShallow(Set<ObjectId> shallowCommits
		for (ObjectId shallowCommit : shallowCommits) {
			output.writeString("shallow " + shallowCommit.name());
		}

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
	}

	private String handleShallowUnshallow(Set<ObjectId> advertisedShallowCommits
			throws IOException {
		String line = input.readString();
		ObjectDatabase objectDatabase = local.getObjectDatabase();
		HashSet<ObjectId> newShallowCommits = new HashSet<>(advertisedShallowCommits);
		while (!PacketLineIn.isDelimiter(line) && !PacketLineIn.isEnd(line)) {
			if (line.startsWith("shallow ")) {
				newShallowCommits.add(ObjectId.fromString(line.substring("shallow ".length())));
			} else if (line.startsWith("unshallow ")) {
				ObjectId unshallow = ObjectId.fromString(line.substring("unshallow ".length()));
				if (!advertisedShallowCommits.contains(unshallow)) {
					throw new PackProtocolException(MessageFormat.format(JGitText.get()
							.notShallowedUnshallow
				}
				newShallowCommits.remove(unshallow);
			}
			line = input.readString();
		}
		objectDatabase.setShallowCommits(newShallowCommits);
		return line;
	}

