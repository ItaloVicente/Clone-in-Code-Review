		int cnt=SyncThread.getDistinctResultCount(25, new Callable<Integer>(){
			public Integer call() throws Exception {
				for(int i=0; i<25; i++) {
					Map<String, Object> m = client.getBulk(keys);
					for(String s : keys) {
						byte b[]=(byte[])m.get(s);
						assert Arrays.hashCode(b) == hashcode
							: "Expected " + hashcode + " was "
								+ Arrays.hashCode(b);
					}
				}
				return hashcode;
			}});
		assertEquals(cnt, 25);
	}
