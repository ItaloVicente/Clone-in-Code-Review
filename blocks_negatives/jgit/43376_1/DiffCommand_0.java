					if (head == null)
						throw new NoHeadException(JGitText.get().cannotReadTree);
					CanonicalTreeParser p = new CanonicalTreeParser();
					ObjectReader reader = repo.newObjectReader();
					try {
						p.reset(reader, head);
					} finally {
						reader.release();
					}
					oldTree = p;
