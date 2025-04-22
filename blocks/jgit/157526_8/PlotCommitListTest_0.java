			Set<Integer> positions = asSet(0
			CommitListAssert test = new CommitListAssert(pcl);
			int posA = test.commit(a5).lanePos(positions).getLanePos();
			test.commit(a4);
			test.commit(a3).lanePos(posA);
			test.commit(e);
			test.commit(d);
			test.commit(a2).lanePos(posA);
			int posB = test.commit(b3).lanePos(positions).getLanePos();
			test.commit(b2).lanePos(posB);
			test.commit(b1).lanePos(posB);
			test.commit(c);
			test.commit(a1).lanePos(posA);
			test.noMoreCommits();
                        assertNotEquals("a lane is the same as b lane"
