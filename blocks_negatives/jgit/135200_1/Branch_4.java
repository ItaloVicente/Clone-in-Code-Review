					throw die(MessageFormat.format(
							CLIText.get().notAValidCommitName, startBranch));
				}
				startBranch = Repository.shortenRefName(startBranch);
				String newRefName = newHead;
				if (!newRefName.startsWith(Constants.R_HEADS)) {
					newRefName = Constants.R_HEADS + newRefName;
				}
				if (!Repository.isValidRefName(newRefName)) {
					throw die(MessageFormat.format(CLIText.get().notAValidRefName, newRefName));
				}
				if (!createForce && db.resolve(newRefName) != null) {
					throw die(MessageFormat.format(CLIText.get().branchAlreadyExists, newHead));
				}
				RefUpdate updateRef = db.updateRef(newRefName);
				updateRef.setNewObjectId(startAt);
				updateRef.setForceUpdate(createForce);
				updateRef.setRefLogMessage(MessageFormat.format(CLIText.get().branchCreatedFrom, startBranch), false);
				Result update = updateRef.update();
				if (update == Result.REJECTED) {
					throw die(MessageFormat.format(CLIText.get().couldNotCreateBranch, newHead, update.toString()));
				}
			} else {
				if (verbose) {
					rw = new RevWalk(db);
