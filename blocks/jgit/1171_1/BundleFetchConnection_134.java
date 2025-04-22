			final Map<ObjectId
			final List<RevObject> commits = new ArrayList<RevObject>();
			for (final Map.Entry<ObjectId
				ObjectId p = e.getKey();
				try {
					final RevCommit c = rw.parseCommit(p);
					if (!c.has(PREREQ)) {
						c.add(PREREQ);
						commits.add(c);
					}
				} catch (MissingObjectException notFound) {
					missing.put(p
				} catch (IOException err) {
					throw new TransportException(transport.uri
							.format(JGitText.get().cannotReadCommit
							err);
