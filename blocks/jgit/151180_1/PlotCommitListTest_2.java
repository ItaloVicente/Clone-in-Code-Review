			CommitListAssert test = new CommitListAssert(pcl);
			test.commit(d).lanePos(0).parents(b
			test.commit(c).lanePos(1).parents(a);
			test.commit(b).lanePos(0).parents(a);
			test.commit(a).lanePos(0).parents();
			test.noMoreCommits();
		}
