			if (shallowCommits != null) {
				if (sectionSent)
					pckOut.writeDelim();
				for (ObjectId o : shallowCommits) {
				}
				for (ObjectId o : unshallowCommits) {
				}
				sectionSent = true;
			}

