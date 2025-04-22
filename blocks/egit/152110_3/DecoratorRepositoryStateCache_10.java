			return UnitOfWork.get(repository, () -> {
				Ref head = getHeadRef(repository);
				if (head == null) {
					return CoreText.RepositoryUtil_noHead;
				}
				if (head.isSymbolic()) {
					String branchName = getFullBranchName(repository);
					return Repository.shortenRefName(branchName);
				}
				ObjectId objectId = head.getObjectId();
				if (objectId == null) {
					return CoreText.RepositoryUtil_noHead;
				}
				String ref = Activator.getDefault().getRepositoryUtil()
						.mapCommitToRef(repository, objectId.name(), false);
				if (ref != null) {
					return Repository.shortenRefName(ref) + ' '
							+ objectId.abbreviate(7).name();
				} else {
					return objectId.abbreviate(7).name();
				}
			});
