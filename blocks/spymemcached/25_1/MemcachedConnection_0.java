
		for(SelectionKey sk : selector.keys()) {
			MemcachedNode mn = (MemcachedNode)sk.attachment();
			if (mn.getContinuousTimeout() > timeoutExceptionThreshold)
			{
				getLogger().info("%s exceeded continuous timeout threshold", sk);
				lostConnection(mn);
			}
		}


