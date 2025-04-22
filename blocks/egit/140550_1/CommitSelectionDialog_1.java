		return (Comparator<RepositoryCommit>) (RepositoryCommit o1, RepositoryCommit o2) -> {
		    int compare = o1.getRepositoryName().compareToIgnoreCase(
			    o2.getRepositoryName());
		    if (compare == 0)
			compare = o1.getRevCommit().compareTo(o2.getRevCommit());
		    return compare;
