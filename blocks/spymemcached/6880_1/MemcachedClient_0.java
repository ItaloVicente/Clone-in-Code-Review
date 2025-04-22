	public SyncFuture<SyncResponse> asyncSync(Collection<SyncRequest> keys,
			int replicaCount, boolean persist, boolean mutation, boolean pandm) {
		final Collection<SyncResponse> m=new LinkedList<SyncResponse>();

		final Map<MemcachedNode, Map<SyncRequest, Integer>> chunks
			=new HashMap<MemcachedNode, Map<SyncRequest, Integer>>();
		final NodeLocator locator=conn.getLocator();
		Iterator<SyncRequest> key_iter=keys.iterator();
		while (key_iter.hasNext()) {
			SyncRequest request=key_iter.next();
			String key=request.getKey();
			int vbucket = bucket.getConfig().getVbucketByKey(key);
			validateKey(key);
			final MemcachedNode primaryNode=locator.getPrimary(key);
			MemcachedNode node=null;
			if(primaryNode.isActive()) {
				node=primaryNode;
			} else {
				for(Iterator<MemcachedNode> i=locator.getSequence(key);
					node == null && i.hasNext();) {
					MemcachedNode n=i.next();
					if(n.isActive()) {
						node=n;
					}
				}
				if(node == null) {
					node=primaryNode;
				}
			}
			assert node != null : "Didn't find a node for " + key;
			Map<SyncRequest, Integer> ks=chunks.get(node);
			if(ks == null) {
				ks=new HashMap<SyncRequest, Integer>();
				chunks.put(node, ks);
			}
			ks.put(request, new Integer(vbucket));
		}

		final CountDownLatch latch=new CountDownLatch(chunks.size());
		final Collection<Operation> ops=new ArrayList<Operation>(chunks.size());

		SyncOperation.Callback cb=new SyncOperation.Callback() {
			@SuppressWarnings("synthetic-access")
			public void receivedStatus(OperationStatus status) {
				if(!status.isSuccess()) {
					getLogger().warn("Unsuccessful get:  %s", status);
				}
			}
			@Override
			public void gotData(SyncResponse s) {
				m.add(s);
			}
			public void complete() {
				latch.countDown();
			}
		};

		final Map<MemcachedNode, Operation> mops=
			new HashMap<MemcachedNode, Operation>();

		for(Map.Entry<MemcachedNode, Map<SyncRequest, Integer>> me
				: chunks.entrySet()) {
			Operation op=opFact.sync(me.getValue(), replicaCount, persist, mutation,
					pandm, cb);
			mops.put(me.getKey(), op);
			ops.add(op);
		}
		assert mops.size() == chunks.size();
		checkState();
		conn.addOperations(mops);
		return new SyncFuture<SyncResponse>(m, ops, latch);
	}

