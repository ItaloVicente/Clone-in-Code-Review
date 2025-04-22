		private void batch(RevWalk walk
			Map<ObjectId
			try (RevWalk rw = new RevWalk(getRepository())) {
				for (ReceiveCommand c : cmds) {
					if (!ObjectId.zeroId().equals(c.getNewId())) {
						try {
							RevObject o = rw.parseAny(c.getNewId());
							RevObject p = rw.peel(o);
							if (o != p) {
								peeled.put(o
							}
						} catch (IOException e) {
							c.setResult(ReceiveCommand.Result.REJECTED_MISSING_OBJECT);
							reject(cmds);
							return;
						}
					}
				}
			}

			for (ReceiveCommand c : cmds) {
				Ref r = refs.get(c.getRefName());
				if (r == null) {
					if (c.getType() != ReceiveCommand.Type.CREATE) {
						c.setResult(ReceiveCommand.Result.LOCK_FAILURE);
						reject(cmds);
						return;
					}
				} else if (r.isSymbolic() || r.getObjectId() == null
						|| !r.getObjectId().equals(c.getOldId())) {
					c.setResult(ReceiveCommand.Result.LOCK_FAILURE);
					reject(cmds);
					return;
				}
			}

			for (ReceiveCommand c : cmds) {
				if (c.getType() == ReceiveCommand.Type.DELETE) {
					refs.remove(c.getRefName());
					c.setResult(ReceiveCommand.Result.OK);
					continue;
				}

				ObjectId p = peeled.get(c.getNewId());
				Ref r;
				if (p != null) {
					r = new ObjectIdRef.PeeledTag(Storage.LOOSE
							c.getRefName()
				} else {
					r = new ObjectIdRef.PeeledNonTag(Storage.LOOSE
							c.getRefName()
				}
				refs.put(r.getName()
				c.setResult(ReceiveCommand.Result.OK);
			}
		}

		private void reject(List<ReceiveCommand> cmds) {
			for (ReceiveCommand c : cmds) {
				if (c.getResult() == ReceiveCommand.Result.NOT_ATTEMPTED) {
					c.setResult(ReceiveCommand.Result.REJECTED_OTHER_REASON
							JGitText.get().transactionAborted);
				}
			}
		}

