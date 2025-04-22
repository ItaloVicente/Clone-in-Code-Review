			try {
				if (cmd.getResult() == NOT_ATTEMPTED) {
					cmd.updateType(walk);
					RefUpdate ru = newUpdate(cmd);
					switch (cmd.getType()) {
					case DELETE:
						update.update(1);
						cmd.setResult(ru.delete(walk));
						commands2.remove(cmd);
					case CREATE:
					case UPDATE:
					case UPDATE_NONFASTFORWARD:
						namesToCheck.add(cmd.getRefName());
						continue;
					}
				}
			} catch (IOException err) {
				cmd.setResult(
						REJECTED_OTHER_REASON
						MessageFormat.format(JGitText.get().lockError
								err.getMessage()));
			}
		}
		Collection<String> takenNames = new HashSet<String>(refdb.getRefs(
				RefDatabase.ALL).keySet());
		Collection<String> takenPrefixes = getTakenPrefixes(takenNames);

		for (ReceiveCommand cmd : commands2) {
