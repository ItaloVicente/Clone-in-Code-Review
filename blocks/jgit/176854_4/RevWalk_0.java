		return getMergedInto(commit
	}

	public List<Ref> getMergedInto(RevCommit commit
					ProgressMonitor monitor) throws IOException{
		return getMergedInto(commit
				GetMergedIntoStrategy.EVALUATE_ALL
				monitor);
