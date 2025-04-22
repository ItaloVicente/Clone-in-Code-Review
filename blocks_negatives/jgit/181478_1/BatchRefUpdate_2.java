						SWITCH: switch (cmd.getType()) {
						case DELETE:
							break;
						case UPDATE:
						case UPDATE_NONFASTFORWARD:
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
							takenPrefixes.addAll(getPrefixes(cmd.getRefName()));
							takenNames.add(cmd.getRefName());
							cmd.setResult(ru.update(walk));
