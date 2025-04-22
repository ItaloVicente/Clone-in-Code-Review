					final CommitBuilder newc = new CommitBuilder();
					newc.setTreeId(emptyTree);
					newc.setAuthor(new PersonIdent(me
					newc.setCommitter(newc.getAuthor());
					newc.setParentIds(newParents);
					t.newId = oi.insert(newc);
					rewrites.put(t.oldId
					pm.update(1);
				}
