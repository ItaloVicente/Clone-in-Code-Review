			ObjectId branch;
			if (orphan) {
				if (startPoint == null && startCommit == null) {
					Result r = repo.updateRef(Constants.HEAD).link(
							getBranchName());
					if (!EnumSet.of(Result.NEW
						throw new RefAlreadyExistsException(
								MessageFormat.format(
										JGitText.get().refAlreadyExists
										r.name()));
					this.status = CheckoutResult.NOT_TRIED_RESULT;
					return repo.getRef(Constants.HEAD);
				}
				branch = getStartPoint();
			} else {
				branch = repo.resolve(name);
				if (branch == null)
					throw new RefNotFoundException(MessageFormat.format(
							JGitText.get().refNotResolved
			}
