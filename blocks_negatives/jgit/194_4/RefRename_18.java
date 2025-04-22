			RefLogWriter.renameTo(db, oldFromDelete,
					newToUpdate);
			newToUpdate.setRefLogMessage(null, false);
			String tmpRefName = "RENAMED-REF.." + Thread.currentThread().getId();
			RefUpdate tmpUpdateRef = db.updateRef(tmpRefName);
			if (renameHEADtoo) {
				try {
					oldFromDelete.db.link(Constants.HEAD, tmpRefName);
				} catch (IOException e) {
					RefLogWriter.renameTo(db,
							newToUpdate, oldFromDelete);
					return renameResult = Result.LOCK_FAILURE;
				}
			}
			tmpUpdateRef.setNewObjectId(oldFromDelete.getOldObjectId());
			tmpUpdateRef.setForceUpdate(true);
			Result update = tmpUpdateRef.update();
			if (update != Result.FORCED && update != Result.NEW && update != Result.NO_CHANGE) {
				RefLogWriter.renameTo(db,
						newToUpdate, oldFromDelete);
				if (renameHEADtoo) {
					oldFromDelete.db.link(Constants.HEAD, oldFromDelete.getName());
				}
				return renameResult = update;
			}

			oldFromDelete.setExpectedOldObjectId(oldFromDelete.getOldObjectId());
			oldFromDelete.setForceUpdate(true);
			Result delete = oldFromDelete.delete();
			if (delete != Result.FORCED) {
				if (db.getRef(
						oldFromDelete.getName()) != null) {
					RefLogWriter.renameTo(db,
							newToUpdate, oldFromDelete);
					if (renameHEADtoo) {
						oldFromDelete.db.link(Constants.HEAD, oldFromDelete
								.getName());
					}
				}
				return renameResult = delete;
			}

			newToUpdate.setNewObjectId(tmpUpdateRef.getNewObjectId());
			Result updateResult = newToUpdate.update();
			if (updateResult != Result.NEW) {
				RefLogWriter.renameTo(db, newToUpdate, oldFromDelete);
				if (renameHEADtoo) {
					oldFromDelete.db.link(Constants.HEAD, oldFromDelete.getName());
				}
				oldFromDelete.setExpectedOldObjectId(null);
				oldFromDelete.setNewObjectId(oldFromDelete.getOldObjectId());
				oldFromDelete.setForceUpdate(true);
				oldFromDelete.setRefLogMessage(null, false);
				Result undelete = oldFromDelete.update();
				if (undelete != Result.NEW && undelete != Result.LOCK_FAILURE)
					return renameResult = Result.IO_FAILURE;
				return renameResult = Result.LOCK_FAILURE;
			}

			if (renameHEADtoo) {
				oldFromDelete.db.link(Constants.HEAD, newToUpdate.getName());
			} else {
				db.fireRefsMaybeChanged();
			}
			RefLogWriter.append(this, newToUpdate.getName(), "Branch: renamed "
					+ db.shortenRefName(oldFromDelete.getName()) + " to "
					+ db.shortenRefName(newToUpdate.getName()));
			if (renameHEADtoo)
				RefLogWriter.append(this, Constants.HEAD, "Branch: renamed "
						+ db.shortenRefName(oldFromDelete.getName()) + " to "
						+ db.shortenRefName(newToUpdate.getName()));
			return renameResult = Result.RENAMED;
		} catch (RuntimeException e) {
			throw e;
