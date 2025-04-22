
		@Override
		public BatchRefUpdate newBatchUpdate() {
			return new BatchRefUpdate(this) {
				@Override
				public void execute(RevWalk walk, ProgressMonitor monitor)
						throws IOException {
					if (performsAtomicTransactions() && isAtomic()) {
						try {
							lock.writeLock().lock();
							batch(getCommands());
						} finally {
							lock.writeLock().unlock();
						}
					} else {
						super.execute(walk, monitor);
					}
				}
			};
		}

		@Override
		protected RefCache scanAllRefs() throws IOException {
			RefList.Builder<Ref> ids = new RefList.Builder<>();
			RefList.Builder<Ref> sym = new RefList.Builder<>();
			try {
				lock.readLock().lock();
				for (Ref ref : refs.values()) {
					if (ref.isSymbolic())
						sym.add(ref);
					ids.add(ref);
				}
			} finally {
				lock.readLock().unlock();
			}
			ids.sort();
			sym.sort();
			objdb.getCurrentPackList().markDirty();
			return new RefCache(ids.toRefList(), sym.toRefList());
		}

		private void batch(List<ReceiveCommand> cmds) {
			Map<ObjectId, ObjectId> peeled = new HashMap<>();
			try (RevWalk rw = new RevWalk(getRepository())) {
				for (ReceiveCommand c : cmds) {
					if (c.getResult() != ReceiveCommand.Result.NOT_ATTEMPTED) {
						ReceiveCommand.abort(cmds);
						return;
					}

					if (!ObjectId.zeroId().equals(c.getNewId())) {
						try {
							RevObject o = rw.parseAny(c.getNewId());
							if (o instanceof RevTag) {
								peeled.put(o, rw.peel(o).copy());
							}
						} catch (IOException e) {
							c.setResult(ReceiveCommand.Result.REJECTED_MISSING_OBJECT);
							ReceiveCommand.abort(cmds);
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
						ReceiveCommand.abort(cmds);
						return;
					}
				} else {
					ObjectId objectId = r.getObjectId();
					if (r.isSymbolic() || objectId == null
							|| !objectId.equals(c.getOldId())) {
						c.setResult(ReceiveCommand.Result.LOCK_FAILURE);
						ReceiveCommand.abort(cmds);
						return;
					}
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
					r = new ObjectIdRef.PeeledTag(Storage.PACKED,
							c.getRefName(), c.getNewId(), p);
				} else {
					r = new ObjectIdRef.PeeledNonTag(Storage.PACKED,
							c.getRefName(), c.getNewId());
				}
				refs.put(r.getName(), r);
				c.setResult(ReceiveCommand.Result.OK);
			}
			clearCache();
		}

		@Override
		protected boolean compareAndPut(Ref oldRef, Ref newRef)
				throws IOException {
			try {
				lock.writeLock().lock();
				ObjectId id = newRef.getObjectId();
				if (id != null) {
					try (RevWalk rw = new RevWalk(getRepository())) {
						rw.parseAny(id);
					}
				}
				String name = newRef.getName();
				if (oldRef == null)
					return refs.putIfAbsent(name, newRef) == null;

				Ref cur = refs.get(name);
				if (cur != null) {
					if (eq(cur, oldRef))
						return refs.replace(name, cur, newRef);
				}

				if (oldRef.getStorage() == Storage.NEW)
					return refs.putIfAbsent(name, newRef) == null;

				return false;
			} finally {
				lock.writeLock().unlock();
			}
		}

		@Override
		protected boolean compareAndRemove(Ref oldRef) throws IOException {
			try {
				lock.writeLock().lock();
				String name = oldRef.getName();
				Ref cur = refs.get(name);
				if (cur != null && eq(cur, oldRef))
					return refs.remove(name, cur);
				else
					return false;
			} finally {
				lock.writeLock().unlock();
			}
		}

		private boolean eq(Ref a, Ref b) {
			if (!Objects.equals(a.getName(), b.getName()))
				return false;
			if (a.isSymbolic() != b.isSymbolic())
				return false;
			if (a.isSymbolic())
				return Objects.equals(a.getTarget().getName(), b.getTarget().getName());
			else
				return Objects.equals(a.getObjectId(), b.getObjectId());
		}
