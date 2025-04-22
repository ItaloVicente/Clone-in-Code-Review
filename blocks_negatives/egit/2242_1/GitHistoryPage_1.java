				currentHeadId = headId;
				if (currentWalk != null)
					currentWalk.release();
				currentWalk = new SWTWalk(db);
				currentWalk.sort(RevSort.COMMIT_TIME_DESC, true);
				currentWalk.sort(RevSort.BOUNDARY, true);
