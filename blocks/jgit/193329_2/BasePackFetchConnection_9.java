
		if (GitProtocolConstants.SECTION_SHALLOW_INFO.equals(line)) {
			ObjectDatabase objectDatabase = local.getObjectDatabase();
			HashSet<ObjectId> shallowCommits = new HashSet<>(objectDatabase.getShallowCommits());
			while (isShallowOrEmptyLine(line = pckIn.readString())) {
				if (line.startsWith("shallow ")) {
					shallowCommits.add(ObjectId.fromString(line.replace("shallow "
				} else if (line.startsWith("unshallow ")) {
					shallowCommits.remove(ObjectId.fromString(line.replace("unshallow "
				}
			}
			objectDatabase.setShallowCommits(shallowCommits);
		}

