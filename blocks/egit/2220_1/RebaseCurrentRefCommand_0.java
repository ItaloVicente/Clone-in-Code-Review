		RevWalk rw = new RevWalk(repository);
		RevCommit commit;
		try {
			commit = rw.parseCommit(ref.getObjectId());
		} catch (MissingObjectException e) {
			throw new ExecutionException("Failed to resolve upstream " + ref, e); //$NON-NLS-1$ FIXME
		} catch (IncorrectObjectTypeException e) {
			throw new ExecutionException("Rebase failed " + ref, e); //$NON-NLS-1$ FIXME
		} catch (IOException e) {
			throw new ExecutionException("Rebase failed " + ref, e); //$NON-NLS-1$ FIXME
		}
		final RebaseOperation rebase = new RebaseOperation(repository, commit);
