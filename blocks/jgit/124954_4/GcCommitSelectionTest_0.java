	@Test
	public void testDistributionOnMultipleBranches() throws Exception {
		BranchBuilder[] branches = { tr.branch("refs/heads/main")
				tr.branch("refs/heads/a")
				tr.branch("refs/heads/c") };
		RevCommit[] tips = new RevCommit[branches.length];
		List<RevCommit> commits = createHistory(branches
		PackConfig config = new PackConfig();
		config.setBitmapContiguousCommitCount(1);
		config.setBitmapRecentCommitSpan(5);
		config.setBitmapDistantCommitSpan(20);
		config.setBitmapRecentCommitCount(100);
		Set<RevCommit> wants = new HashSet<>(Arrays.asList(tips));
		PackWriterBitmapPreparer preparer = newPreparer(wants
		List<BitmapCommit> selection = new ArrayList<>(
				preparer.selectCommits(commits.size()
		Set<ObjectId> selected = new HashSet<>();
		for (BitmapCommit c : selection) {
			selected.add(c.toObjectId());
		}

		for (RevCommit c : wants) {
			assertTrue(selected.contains(c.toObjectId()));
			int count = 1;
			int selectedCount = 1;
			RevCommit parent = c;
			while (parent.getParentCount() != 0) {
				parent = parent.getParent(0);
				count++;
				if (selected.contains(parent.toObjectId())) {
					selectedCount++;
				}
			}
			int expectedCount = 10 + (count - 100 - 24) / 25;
			assertTrue(expectedCount <= selectedCount);
		}
	}

	private List<RevCommit> createHistory(BranchBuilder[] branches
			RevCommit[] tips) throws Exception {
		List<RevCommit> commits = new ArrayList<>();
		String[] prefixes = { "m"
		int branchCount = branches.length;
		tips[0] = addCommit(commits
		int counter = 0;

		for (int b = 0; b < branchCount; b++) {
			for (int i = 0; i < 100; i++
				for (int j = 0; j <= b; j++) {
					tips[j] = addCommit(commits
							prefixes[j] + counter);
				}
			}
			if (b < branchCount - 1) {
				tips[b + 1] = addCommit(branches[b + 1]
						"branch_" + prefixes[b + 1]
			}
		}
		return commits;
	}

	private RevCommit addCommit(List<RevCommit> commits
			String msg
		CommitBuilder commit = bb.commit().message(msg).add(msg
		if (parents.length > 0) {
			commit.noParents();
			for (RevCommit parent : parents) {
				commit.parent(parent);
			}
		}
		RevCommit c = commit.create();
		tr.parseBody(c);
		commits.add(c);
		return c;
	}

	private PackWriterBitmapPreparer newPreparer(Set<RevCommit> wants
			List<RevCommit> commits
