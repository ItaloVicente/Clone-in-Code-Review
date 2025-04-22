					RefRename r = db.renameRef(src
					if (r.rename() != Result.RENAMED) {
						throw die(MessageFormat
								.format(CLIText.get().cannotBeRenamed
					}
				} else if (createForce || branch != null) {
					String newHead = branch;
					String startBranch;
					if (createForce) {
						startBranch = otherBranch;
					} else {
						startBranch = Constants.HEAD;
					}
					Ref startRef = db.findRef(startBranch);
					if (startRef != null) {
						startBranch = startRef.getName();
					} else if (startAt != null) {
						startBranch = startAt.name();
					} else {
						throw die(MessageFormat.format(
								CLIText.get().notAValidCommitName
								startBranch));
					}
					startBranch = Repository.shortenRefName(startBranch);
					String newRefName = newHead;
					if (!newRefName.startsWith(Constants.R_HEADS)) {
						newRefName = Constants.R_HEADS + newRefName;
					}
					if (!Repository.isValidRefName(newRefName)) {
						throw die(MessageFormat.format(
								CLIText.get().notAValidRefName
					}
					if (!createForce && db.resolve(newRefName) != null) {
						throw die(MessageFormat.format(
								CLIText.get().branchAlreadyExists
					}
					RefUpdate updateRef = db.updateRef(newRefName);
					updateRef.setNewObjectId(startAt);
					updateRef.setForceUpdate(createForce);
					updateRef.setRefLogMessage(MessageFormat.format(
							CLIText.get().branchCreatedFrom
							false);
					Result update = updateRef.update();
					if (update == Result.REJECTED) {
						throw die(MessageFormat.format(
								CLIText.get().couldNotCreateBranch
								update.toString()));
					}
