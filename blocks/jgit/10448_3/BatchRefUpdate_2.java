				cmd.setResult(
						REJECTED_OTHER_REASON
						MessageFormat.format(JGitText.get().lockError
								err.getMessage()));
			}
		}
		if (!commands2.isEmpty()) {
			Collection<String> takenNames = new HashSet<String>(refdb.getRefs(
					RefDatabase.ALL).keySet());
			Collection<String> takenPrefixes = getTakenPrefixes(takenNames);

			for (ReceiveCommand cmd : commands2) {
				try {
					monitor.update(1);

					if (cmd.getResult() == NOT_ATTEMPTED) {
						cmd.updateType(walk);
						RefUpdate ru = newUpdate(cmd);
						SWITCH: switch (cmd.getType()) {
						case DELETE:
							break;
						case UPDATE:
						case UPDATE_NONFASTFORWARD:
							monitor.update(1);
							RefUpdate ruu = newUpdate(cmd);
							cmd.setResult(ruu.update(walk));
							break;
						case CREATE:
							for (String prefix : getPrefixes(cmd.getRefName())) {
								if (takenNames.contains(prefix)) {
									cmd.setResult(Result.LOCK_FAILURE);
									break SWITCH;
								}
							}
							if (takenPrefixes.contains(cmd.getRefName())) {
								cmd.setResult(Result.LOCK_FAILURE);
								break SWITCH;
							}
							ru.setCheckConflicting(false);
							addRefToPrefixes(takenPrefixes
							takenNames.add(cmd.getRefName());
							cmd.setResult(ru.update(walk));
						}
					}
				} catch (IOException err) {
					cmd.setResult(REJECTED_OTHER_REASON
							JGitText.get().lockError
				}
