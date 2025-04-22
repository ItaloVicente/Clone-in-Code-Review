				final CommitBuilder newc = new CommitBuilder();
				newc.setTreeId(emptyTree);
				newc.setAuthor(new PersonIdent(me, new Date(t.commitTime)));
				newc.setCommitter(newc.getAuthor());
				newc.setParentIds(newParents);
				t.newId = oi.insert(newc);
				rewrites.put(t.oldId, t.newId);
				pm.update(1);
