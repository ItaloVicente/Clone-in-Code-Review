			int remaining = commits.size();
			try {
				RevCommit c;
				while ((c = rw.next()) != null) {
					if (c.has(PREREQ)) {
						c.add(SEEN);
						if (--remaining == 0)
							break;
					}
