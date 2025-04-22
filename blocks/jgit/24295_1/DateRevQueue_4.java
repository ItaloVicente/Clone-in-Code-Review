	public class RevCommitTimeComparator implements Comparator<RevCommit> {
		public int compare(RevCommit o1
			if (o1.commitTime > o2.commitTime)
				return -1;
			if (o1.commitTime < o2.commitTime)
				return 1;
			return 0;
		}
