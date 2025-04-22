				if (findInId && notFound) {
					String id = revision.getId().name();
					if (id.indexOf(findPattern) != -1) {
						totalMatches++;
						findResults.add(i, revision);
						notFound = false;
					}
				}

