						for (String prefix : getPrefixes(cmd.getRefName())) {
							if (takenNames.contains(prefix)) {
								cmd.setResult(Result.LOCK_FAILURE);
								continue;
							}
						}
						if (takenPrefixes.contains(cmd.getRefName())) {
							cmd.setResult(Result.LOCK_FAILURE);
							continue;
						}
						ru.setCheckConflicting(false);
						addRefToPrefixes(takenPrefixes
						takenNames.add(cmd.getRefName());
