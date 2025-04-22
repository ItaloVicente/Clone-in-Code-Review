	private void sendShallow(Set<ObjectId> shallowCommits
		for (ObjectId shallowCommit : shallowCommits) {
		}

		if (depth != null) {
		}

		if (deepenSince != null) {
		}

		if (deepenNots != null) {
			for (String deepenNotRef : deepenNots) {
			}
		}
	}

	private String handleShallowUnshallow(Set<ObjectId> advertisedShallowCommits
			throws IOException {
		String line = input.readString();
		ObjectDatabase objectDatabase = local.getObjectDatabase();
		HashSet<ObjectId> newShallowCommits = new HashSet<>(advertisedShallowCommits);
		while (!PacketLineIn.isDelimiter(line) && !PacketLineIn.isEnd(line)) {
				newShallowCommits.add(ObjectId
				ObjectId unshallow = ObjectId
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

