		Collections.sort(this.commits, new Comparator<RevCommit>() {
			public int compare(RevCommit o1, RevCommit o2) {
				return o1.getAuthorIdent().getWhen()
						.compareTo(o2.getAuthorIdent().getWhen());
			}
		});
