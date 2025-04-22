	
	public void testSync() throws Exception {
		if (isMembase()) {
			client = new MembaseClient(Arrays.asList(new URI(
					"http://localhost:8091/pools")), "default", "default", "");
			Collection<SyncRequest> keys = new LinkedList<SyncRequest>();

			for (int i = 0; i < 20; i++) {
				keys.add(new SyncRequest("key" + i));
			}

			for (int i = 0; i < 10; i++) {
				client.set("key" + i, 0, "value" + i);
			}

			Collection<SyncResponse> resp = ((MembaseClient)client).asyncSync(keys, 0, false,
					false, false).get();
			Iterator<SyncResponse> itr = resp.iterator();
			int passed = 0;
			int failed = 0;
			while (itr.hasNext()) {
				SyncResponse cur = itr.next();
				if (cur.getStatus().isSuccess()) {
					passed++;
				} else {
					failed++;
				}
			}
			System.out.println(passed + " " + failed);
			client.flush();
		}
	}
