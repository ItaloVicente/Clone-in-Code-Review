
	@Test
	public void testRepo1Changes() throws Exception {
		testCommitGraphTopology(
				"**********************************************************"
						+ "**********************************************************"
						+ "**********************************************************"
						+ "**********************************************************"
						+ "***2*12*2*21**********3*21**2*12*8*21******4*21**7*21*3*21*"
						+ "2*21**3*21****2**13***5*2**2*14***7*2*37*13*13****8*2*5*23*"
						+ "12***a*31*3*21**2***24**13********5*21********2*2*3*21*14*"
						+ "******a*21*3*21*2*21****5*21***2*2*21********3*21*4*21*5*"
						+ "21*****3*2******17*8*21*2*21*2*21***2*21*2*21*2*21*9*21*4*"
						+ "21*2*21*2*21*******2*12**3*21****************7*31*4*21*15*"
						+ "6*21***2*2**13*4*21*7*5*31*13*3*21**3*21*2*21*2*21**3*21**"
						+ "**2*21*3*21*2*21****f*21*3*21*9*21***4*21***2*8*21*7*21*16*"
						+ "****2**3*24*12**6*21*******8*21**3*31*13*7*21**3**31*5***4*"
						+ "2*2**3**3*2*2*h*9*41*43*6*31*2*21*7*21*7*2*******2*21*3*21*"
						+ "c*2*****2*21************"
	}

	@Test
	public void testRepo2Changes() throws Exception {
		testCommitGraphTopology(
				"**2*21*3*21**********3*21**2*21*8*21******4*21**7*21*3*21*2*"
						+ "21**3*21*****3*21*****8*21***6*21*6*21*5*21*****8*21*6*"
						+ "21**9*21*3**21***3*21**4*21********5*21*********2*21*5*"
						+ "21*******b*21*3*21*2*21****5*21***2**31********3*21*4*"
						+ "21*5*21***********9*21*2*21*2*21*2*21***2*21*2*21*2*21*"
						+ "9*21*4*21*2*21*2*21*******2*21**4*21***************6*2"
						+ "1*3*21*5*21*2*21*****4*21*2*21*5*21*9*21*4*21**3*21*2*"
						+ "21*2*21**3*21****2*21*3*21*2*21****f*21*3*21*9*21***4*"
						+ "21***7*21*6*21*6*21******3*21*2*21**7*21*******8*21*2*"
						+ "21*3*21*7*21**3**31******a*21***e*******4*21*3*21*2*21*"
						+ "6*21********2*21*3*21******2*21************"
	}

	@Test
	public void testRepo3Changes() throws Exception {
		testCommitGraphTopology(
				"**2*3*4*2******19*1b*5*2*3*2**15*14***2*2**2*12***17***k*2*"
						+ "3*4*5*25***2*******1e*1c*1f**1c**K*2*"
	}

	public void testCommitGraphTopology(String graphDescription
			boolean tagAllCommits)
			throws Exception {
		List<RevCommit> commits = createCommitGraph(graphDescription);

		PlotWalk pw = new PlotWalk(db);
		int tnr = 0;
		for (RevCommit c : commits) {
			pw.markStart(pw.lookupCommit(c.getId()));
			if (tagAllCommits)
				ltag("tag" + tnr++
		}

		PlotCommitList<PlotLane> pcl = new PlotCommitList<PlotLane>();
		pcl.source(pw);
		pcl.fillTo(Integer.MAX_VALUE);

		for (int i = 0; i < pcl.size(); i++) {
			PlotCommit<PlotLane> pc = pcl.get(i);
			int myPos = pc.getLane().getPosition();
			for (PlotLane l : pc.passingLanes)
				assertTrue("Commit #" + i
						+ " is located on a position of a passing lane " + l
						myPos != l.position);
			int ci = lookupIndex(commits
			RevCommit c = commits.get(ci);

			assertEquals("Not the same number of parents on commit #" + i
					+ ": " + c.toString()
					c.getParentCount());
			assertEquals("Not the same number of children on commit #" + i
					+ ": " + c.toString()
					getChildrenCount(commits
		}
	}

	public void ltag(String name
		RefUpdate tagRef = db.updateRef(Constants.R_TAGS + name);
		tagRef.setNewObjectId(tgt);
		tagRef.setForceUpdate(true);
		tagRef.setRefLogMessage("tagged " + name
		tagRef.update();
	}

	private int lookupIndex(List<RevCommit> commits
		for (int j = i; j < commits.size(); j++)
			if (commits.get(j).getId().equals(id))
				return j;
		for (int j = 0; j < i; j++)
			if (commits.get(j).getId().equals(id))
				return j;
		return -1;
	}

	private int getChildrenCount(List<RevCommit> commits
		int nrOfChildren = 0;
		for (RevCommit c : commits) {
			for (RevCommit aktParent : c.getParents()) {
				if (aktParent.equals(parent))
					nrOfChildren++;
			}
		}
		return nrOfChildren;
	}

	public List<RevCommit> createCommitGraph(String graphDescription)
			throws Exception {
		RevCommit lastCommit = null;
		byte[] bytes = graphDescription.getBytes();
		List<RevCommit> commits = new ArrayList<RevCommit>();
		List<RevCommit> parents = new ArrayList<RevCommit>();
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] != '*') {
				do
					parents.add(commits.get(commits.size()
							- hexChar2int(bytes[i++])));
				while (bytes[i] != '*');
				lastCommit = commit(parents.toArray(new RevCommit[parents
						.size()]));
				parents.clear();
			} else
				lastCommit = (lastCommit == null) ? commit()
						: commit(lastCommit);
			commits.add(parseBody(lastCommit));
		}
		Collections.reverse(commits);
		return commits;
	}

	public static int hexChar2int(byte b) {
		if (b >= '0' && b <= '9')
			return b - '0';
		if (b >= 'a' && b <= 'z')
			return 10 + b - 'a';
		if (b >= 'A' && b <= 'Z')
			return 36 + b - 'A';
		else
			throw new IllegalArgumentException();
	}
