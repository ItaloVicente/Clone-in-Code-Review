			for (int i = 0; i < memberSnapshots.length; i++) {
				if (!fileNameExcludes.contains(memberSnapshots[i].name)) {
					if (!memberSnapshots[i].isValid(resource)) {
						return false;
					}
				}
