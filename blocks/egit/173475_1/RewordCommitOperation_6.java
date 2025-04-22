			}
			progress.worked(10);
			progress.setWorkRemaining(commits.size() + 2);
			PersonIdent committer = new PersonIdent(repository);
			CommitBuilder builder = copy(commit, commit.getParents(), committer,
					newMessage);
			GpgConfig gpgConfig = new GpgConfig(repository.getConfig());
			boolean signAllCommits = gpgConfig.isSignCommits();
			String keyId = gpgConfig.getSigningKey();
			GpgSigner gpgSigner = GpgSigner.getDefault();
			if (gpgSigner != null && (signAllCommits
					|| commit.getRawGpgSignature() != null)) {
				gpgSigner = sign(builder, gpgSigner, signAllCommits, keyId,
						committer, commit.getCommitterIdent(), commit);
			}
			Map<ObjectId, ObjectId> rewritten = new HashMap<>();
			try (ObjectInserter inserter = repository.newObjectInserter()) {
				rewritten.put(commit.getId(), inserter.insert(builder));
