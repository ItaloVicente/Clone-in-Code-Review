			if (!wantedRefs.isEmpty()) {
				if (sectionSent) {
					pckOut.writeDelim();
				}
				for (Map.Entry<String, ObjectId> entry : wantedRefs.entrySet()) {
					pckOut.writeString(entry.getValue().getName() + ' ' +
							entry.getKey() + '\n');
				}
				sectionSent = true;
			}

