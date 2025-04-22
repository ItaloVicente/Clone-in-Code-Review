				assertEquals(merge
				assertEquals(sourceCommit
			} else {
				if (expectedPullMode == TestPullMode.REBASE_PREASERVE) {
					next = rw.next();
					assertEquals(2
				}
				next = rw.next();
				assertEquals(t2.getShortMessage()
				next = rw.next();
				assertEquals(t1.getShortMessage()
				next = rw.next();
				assertEquals(sourceCommit
				next = rw.next();
				assertEquals("Initial commit for source"
						next.getShortMessage());
				next = rw.next();
				assertNull(next);
