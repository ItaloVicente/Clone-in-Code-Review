			}
			SWTCommit[] asArray = null;
			synchronized (loadedCommits) {
				int count = loadedCommits.size();
				if (count < listSize) {
					return;
				}
				asArray = loadedCommits.toArray(new SWTCommit[0]);
			}
