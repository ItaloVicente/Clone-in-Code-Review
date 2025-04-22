				if (continuousTimeout.get() > timeoutExceptionThreshold) {
					MemcachedNode mn = (MemcachedNode)sk.attachment();
					lostConnection(mn);
				} else {
					handleIO(sk);
				}
