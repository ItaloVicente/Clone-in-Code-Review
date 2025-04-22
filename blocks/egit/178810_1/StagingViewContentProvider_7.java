			for (String file : indexDiff.getConflicting()) {
				StagingEntry newEntry = new StagingEntry(repository,
						CONFLICTING, file, this::getFile);
				newEntry.setConflictType(
						indexDiff.getConflictStates().get(file));
				nodes.add(newEntry);
			}
