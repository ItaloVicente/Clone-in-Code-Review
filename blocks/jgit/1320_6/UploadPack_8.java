
			if (line.startsWith("deepen ")) {
				depth = Integer.parseInt(line.substring(7));
				continue;
			}

			if (line.startsWith("shallow ")) {
				clientShallowCommits.add(ObjectId.fromString(line.substring(8)));
				continue;
			}

