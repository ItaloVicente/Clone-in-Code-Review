
			if (line.startsWith("deepen ")) {
				depth = Integer.parseInt(line.substring(7));
				continue;
			}
			if (line.startsWith("shallow ")) {
				final ObjectId id = ObjectId.fromString(line.substring(8));
				remoteShallowCommits.add(walk.parseCommit(id));
				continue;
			}
