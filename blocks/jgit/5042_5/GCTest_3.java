	@Test
	public void emptyRepo_noPackCreated() throws IOException {
		GC.gc(null
		assertEquals(0
	}

	@Test
	public void oneNonReferencedObject_pruned() throws Exception {
		tr.blob("a");
		GC.gc(null
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void nonReferencedObjectTree_pruned() throws Exception {
		tr.tree(tr.file("a"
		GC.gc(null
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void lightweightTag_objectNotPruned() throws Exception {
		RevBlob a = tr.blob("a");
		tr.lightweightTag("t"
		GC.gc(null
		assertEquals(1
		assertTrue(packedObjectIDs().contains(a.name()));
		assertEquals(0
	}

	@Test
	public void annotatedTag_objectNotPruned() throws Exception {
		RevBlob a = tr.blob("a");
		RevTag t = tr.tag("t"
		tr.lightweightTag("t"

		GC.gc(null
		assertEquals(1
		assertTrue(packedObjectIDs().contains(a.name()));
		assertTrue(packedObjectIDs().contains(t.name()));
		assertEquals(0
	}

	@Test
	public void branch_historyNotPruned() throws Exception {
		RevCommit tip = commitChain(10);
		tr.branch("b").update(tip);
		GC.gc(null
		assertEquals(1
		Set<String> packed = packedObjectIDs();
		do {
			assertTrue(packed.contains(tip.name()));
			tr.parseBody(tip);
			RevTree t = tip.getTree();
			assertTrue(packed.contains(t.name()));
			assertTrue(packed.contains(tr.get(t
			tip = tip.getParentCount() > 0 ? tip.getParent(0) : null;
		} while (tip != null);

		assertEquals(0
	}

	@Test
	public void deleteBranch_historyPruned() throws Exception {
		RevCommit tip = commitChain(10);
		tr.branch("b").update(tip);
		RefUpdate update = repo.updateRef("refs/heads/b");
		update.setForceUpdate(true);
		update.delete();
		GC.gc(null
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void deleteOneBranch_otherBranchHistoryNotPruned() throws Exception {
		RevCommit parent = tr.commit().create();
		tr.branch("b1").commit().parent(parent).create();
		tr.branch("b2").commit().parent(parent).create();
		RefUpdate update = repo.updateRef("refs/heads/b2");
		update.setForceUpdate(true);
		update.delete();
		GC.gc(null
		assertEquals(1
		assertTrue(packedObjectIDs().contains(parent.name()));
		assertEquals(0
	}

	@Test
	public void deleteMergedBranch_historyNotPruned() throws Exception {
		RevCommit parent = tr.commit().create();
		RevCommit b1Tip = tr.branch("b1").commit().add("x"
				.create();
		RevCommit b2Tip = tr.branch("b2").commit().add("y"
				.create();

		Merger merger = MergeStrategy.OURS.newMerger(repo);
		merger.merge(b1Tip

		RefUpdate update = repo.updateRef("refs/heads/b2");
		update.setForceUpdate(true);
		update.delete();

		GC.gc(null

		assertEquals(1
		assertTrue(packedObjectIDs().contains(b2Tip.name()));
		assertEquals(0
	}

	@Test
	public void concurrentGC_onlyOneWritesPack() throws Exception {
		RevBlob a = tr.blob("a");
		tr.lightweightTag("t"

		final CountDownLatch packFileOpenForWrite = new CountDownLatch(1);
		final CyclicBarrier syncPoint = new CyclicBarrier(2);

		class SyncOnWriteObjectsMonitor extends EmptyProgressMonitor {
			boolean writingPack = false;

			public void beginTask(String title
				if (title.equals(JGitText.get().writingObjects)) {
					writingPack = true;
					packFileOpenForWrite.countDown();
					try {
						syncPoint.await();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					} catch (BrokenBarrierException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		class DoGC implements Callable<Void> {
			private ProgressMonitor monitor;

			DoGC(ProgressMonitor monitor) {
				this.monitor = monitor;
			}

			public Void call() throws Exception {
				try {
					GC.gc(monitor
					return null;
				} catch (Exception e) {
					Thread.currentThread().interrupt();
					try {
						syncPoint.await();
					} catch (InterruptedException ignored) {
					}
					throw e;
				}
			}
		}

		ExecutorService pool = Executors.newFixedThreadPool(2);
		try {
			SyncOnWriteObjectsMonitor gc1Monitor = new SyncOnWriteObjectsMonitor();
			SyncOnWriteObjectsMonitor gc2Monitor = new SyncOnWriteObjectsMonitor();
			Future<Void> gc1Result = pool.submit(new DoGC(gc1Monitor));
			packFileOpenForWrite.await();
			Future<Void> gc2Result = pool.submit(new DoGC(gc2Monitor));
			gc1Result.get();
			assertTrue(gc1Monitor.writingPack);
			try {
				gc2Result.get();
				assertFalse("Concurrent threads wrote in the same pack file"
						gc2Monitor.writingPack);
			} catch (ExecutionException e) {
			}
		} finally {
			pool.shutdown();
			pool.awaitTermination(Long.MAX_VALUE
		}
	}




