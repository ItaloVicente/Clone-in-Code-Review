
	@Test
	public void packRefs_looseRefPacked() throws Exception {
		RevBlob a = tr.blob("a");
		tr.lightweightTag("t"

		gc.packRefs();
		assertSame(repo.getRef("t").getStorage()
	}

	@Test
	public void concurrentPackRefs_onlyOneWritesPackedRefs() throws Exception {
		RevBlob a = tr.blob("a");
		tr.lightweightTag("t"

		final CyclicBarrier syncPoint = new CyclicBarrier(2);

		Callable<Integer> packRefs = new Callable<Integer>() {

			public Integer call() throws Exception {
				syncPoint.await();
				try {
					gc.packRefs();
					return 0;
				} catch (IOException e) {
					return 1;
				}
			}
		};
		ExecutorService pool = Executors.newFixedThreadPool(2);
		try {
			Future<Integer> p1 = pool.submit(packRefs);
			Future<Integer> p2 = pool.submit(packRefs);
			assertTrue(p1.get() + p2.get() == 1);
		} finally {
			pool.shutdown();
			pool.awaitTermination(Long.MAX_VALUE
		}
	}

	@Test
	public void packRefsWhileRefLocked_refNotPackedNoError()
			throws Exception {
		RevBlob a = tr.blob("a");
		tr.lightweightTag("t1"
		tr.lightweightTag("t2"
		LockFile refLock = new LockFile(new File(repo.getDirectory()
				"refs/tags/t1")
		try {
			refLock.lock();
			gc.packRefs();
		} finally {
			refLock.unlock();
		}

		assertSame(repo.getRef("refs/tags/t1").getStorage()
		assertSame(repo.getRef("refs/tags/t2").getStorage()
	}

	@Test
	public void packRefsWhileRefUpdated_refUpdateSucceeds()
			throws Exception {
		RevBlob a = tr.blob("a");
		tr.lightweightTag("t"
		final RevBlob b = tr.blob("b");

		final CyclicBarrier refUpdateLockedRef = new CyclicBarrier(2);
		final CyclicBarrier packRefsDone = new CyclicBarrier(2);
		ExecutorService pool = Executors.newFixedThreadPool(2);
		try {
			Future<Result> result = pool.submit(new Callable<Result>() {

				public Result call() throws Exception {
					RefUpdate update = new RefDirectoryUpdate(
							(RefDirectory) repo.getRefDatabase()
							repo.getRef("refs/tags/t")) {
						@Override
						public boolean isForceUpdate() {
							try {
								refUpdateLockedRef.await();
								packRefsDone.await();
							} catch (InterruptedException e) {
								Thread.currentThread().interrupt();
							} catch (BrokenBarrierException e) {
								Thread.currentThread().interrupt();
							}
							return super.isForceUpdate();
						}
					};
					update.setForceUpdate(true);
					update.setNewObjectId(b);
					return update.update();
				}
			});

			pool.submit(new Callable<Void>() {
				public Void call() throws Exception {
					refUpdateLockedRef.await();
					gc.packRefs();
					packRefsDone.await();
					return null;
				}
			});

			assertSame(result.get()

		} finally {
			pool.shutdownNow();
			pool.awaitTermination(Long.MAX_VALUE
		}

		assertEquals(repo.getRef("refs/tags/t").getObjectId()
	}


	@Test
	public void repackEmptyRepo_noPackCreated() throws IOException {
		gc.repack();
		assertEquals(0
	}

	@Test
	public void concurrentRepack() throws Exception {
		final CyclicBarrier syncPoint = new CyclicBarrier(2);

		class DoRepack extends EmptyProgressMonitor implements
				Callable<Integer> {

			public void beginTask(String title
				if (title.equals(JGitText.get().writingObjects)) {
					try {
						syncPoint.await();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					} catch (BrokenBarrierException ignored) {
					}
				}
			}

			public Integer call() throws Exception {
				try {
					gc.setProgressMonitor(this);
					gc.repack();
					return 0;
				} catch (IOException e) {
					Thread.currentThread().interrupt();
					try {
						syncPoint.await();
					} catch (InterruptedException ignored) {
					}
					return 1;
				}
			}
		}

		RevBlob a = tr.blob("a");
		tr.lightweightTag("t"

		ExecutorService pool = Executors.newFixedThreadPool(2);
		try {
			DoRepack repack1 = new DoRepack();
			DoRepack repack2 = new DoRepack();
			Future<Integer> result1 = pool.submit(repack1);
			Future<Integer> result2 = pool.submit(repack2);
			assertTrue(result1.get() + result2.get() == 0);
		} finally {
			pool.shutdown();
			pool.awaitTermination(Long.MAX_VALUE
		}
	}


	@Test
	public void nonReferencedNonExpiredObject_notPruned() throws Exception {
		long start = now();

		fsTick();
		RevBlob a = tr.blob("a");
		long delta = now() - start;
		gc.setExpireAgeMillis(delta);
		gc.prune(Collections.<ObjectId> emptySet());
		assertTrue(repo.hasObject(a));
	}

	@Test
	public void nonReferencedExpiredObject_pruned() throws Exception {
		RevBlob a = tr.blob("a");
		gc.setExpireAgeMillis(0);
		gc.prune(Collections.<ObjectId> emptySet());
		assertFalse(repo.hasObject(a));
	}

	@Test
	public void nonReferencedExpiredObjectTree_pruned() throws Exception {
		RevBlob a = tr.blob("a");
		RevTree t = tr.tree(tr.file("a"
		gc.setExpireAgeMillis(0);
		gc.prune(Collections.<ObjectId> emptySet());
		assertFalse(repo.hasObject(t));
		assertFalse(repo.hasObject(a));
	}

	@Test
	public void nonReferencedObjects_onlyExpiredPruned() throws Exception {
		RevBlob a = tr.blob("a");

		fsTick();
		long start = now();

		fsTick();
		RevBlob b = tr.blob("b");
		gc.setExpireAgeMillis(now() - start);
		gc.prune(Collections.<ObjectId> emptySet());
		assertFalse(repo.hasObject(a));
		assertTrue(repo.hasObject(b));
	}

	@Test
	public void lightweightTag_objectNotPruned() throws Exception {
		RevBlob a = tr.blob("a");
		tr.lightweightTag("t"
		gc.setExpireAgeMillis(0);
		gc.prune(Collections.<ObjectId> emptySet());
		assertTrue(repo.hasObject(a));
	}

	@Test
	public void annotatedTag_objectNotPruned() throws Exception {
		RevBlob a = tr.blob("a");
		RevTag t = tr.tag("t"
		tr.lightweightTag("t"

		gc.setExpireAgeMillis(0);
		gc.prune(Collections.<ObjectId> emptySet());
		assertTrue(repo.hasObject(t));
		assertTrue(repo.hasObject(a));
	}

	@Test
	public void branch_historyNotPruned() throws Exception {
		RevCommit tip = commitChain(10);
		tr.branch("b").update(tip);
		gc.setExpireAgeMillis(0);
		gc.prune(Collections.<ObjectId> emptySet());
		do {
			assertTrue(repo.hasObject(tip));
			tr.parseBody(tip);
			RevTree t = tip.getTree();
			assertTrue(repo.hasObject(t));
			assertTrue(repo.hasObject(tr.get(t
			tip = tip.getParentCount() > 0 ? tip.getParent(0) : null;
		} while (tip != null);
	}

	@Test
	public void deleteBranch_historyPruned() throws Exception {
		RevCommit tip = commitChain(10);
		tr.branch("b").update(tip);
		RefUpdate update = repo.updateRef("refs/heads/b");
		update.setForceUpdate(true);
		update.delete();
		gc.setExpireAgeMillis(0);
		gc.prune(Collections.<ObjectId> emptySet());
		assertTrue(gc.getStatistics().numberOfLooseObjects == 0);
	}

	@Test
	public void deleteMergedBranch_historyNotPruned() throws Exception {
		RevCommit parent = tr.commit().create();
		RevCommit b1Tip = tr.branch("b1").commit().parent(parent).add("x"
				.create();
		RevCommit b2Tip = tr.branch("b2").commit().parent(parent).add("y"
				.create();

		Merger merger = MergeStrategy.SIMPLE_TWO_WAY_IN_CORE.newMerger(repo);
		merger.merge(b1Tip
		CommitBuilder cb = tr.commit();
		cb.parent(b1Tip).parent(b2Tip);
		cb.setTopLevelTree(merger.getResultTreeId());
		RevCommit mergeCommit = cb.create();
		RefUpdate u = repo.updateRef("refs/heads/b1");
		u.setNewObjectId(mergeCommit);
		u.update();

		RefUpdate update = repo.updateRef("refs/heads/b2");
		update.setForceUpdate(true);
		update.delete();

		gc.setExpireAgeMillis(0);
		gc.prune(Collections.<ObjectId> emptySet());
		assertTrue(repo.hasObject(b2Tip));
	}

