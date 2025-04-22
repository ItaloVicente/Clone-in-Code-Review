			if (!wantedRefs.isEmpty()) {
				if (sectionSent)
					pckOut.writeDelim();
				for (Map.Entry<String
					pckOut.writeString(entry.getValue().getName() + ' ' +
							entry.getKey() + '\n');
				}
				sectionSent = true;
			}

