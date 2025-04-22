			CommitFileRevision revision = (CommitFileRevision) element;
			try {
				IStorage storage = revision.getStorage(new NullProgressMonitor());
				return new Data(revision.getRepository(),
						revision.getGitPath(), storage, revision.getRevCommit());
			} catch (CoreException e) {
				return null;
			}
