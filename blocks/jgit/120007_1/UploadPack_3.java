
		if (shallowCommits != null) {
			if (sectionSent)
				pckOut.writeDelim();
			pckOut.writeString("shallow-info\n");
			for (ObjectId o : shallowCommits) {
				pckOut.writeString("shallow " + o.getName() + "\n");
			}
			for (ObjectId o : unshallowCommits) {
				pckOut.writeString("unshallow " + o.getName() + "\n");
			}
			sectionSent = true;
		}

