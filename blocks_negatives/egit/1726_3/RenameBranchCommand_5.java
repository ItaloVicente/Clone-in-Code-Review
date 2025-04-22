				String newName = prefix + newNameDialog.getValue();
				r = db.renameRef(oldName, newName);
				if (r.rename() != Result.RENAMED)
					MessageDialog.openError(shell,
							UIText.RepositoriesView_RenameBranchTitle,
							UIText.RepositoriesView_RenameBranchFailure);
			} catch (IOException e) {
