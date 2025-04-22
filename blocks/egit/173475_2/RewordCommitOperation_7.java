				for (RevCommit c : commits) {
					RevCommit[] parents = c.getParents();
					ObjectId[] newParents = new ObjectId[parents.length];
					int i = 0;
					boolean hadNew = false;
					for (RevCommit p : parents) {
						ObjectId newId = rewritten.get(p.getId());
						if (newId != null) {
							hadNew = true;
						}
						newParents[i++] = newId != null ? newId : p.getId();
					}
					if (!hadNew) {
						continue;
					}
					committer = new PersonIdent(committer); // Update when
					builder = copy(c, newParents, committer,
							c.getFullMessage());
					if (gpgSigner != null && (signAllCommits
							|| c.getRawGpgSignature() != null)) {
						gpgSigner = sign(builder, gpgSigner, signAllCommits,
								keyId, committer, c.getCommitterIdent(), c);
					}
					rewritten.put(c.getId(), inserter.insert(builder));
					progress.worked(1);
				}
				inserter.flush();
