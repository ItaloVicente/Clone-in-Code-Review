	public static void main(String args[]) throws Exception {
		MemcachedClient c=new MemcachedClient(
			AddrUtil.getAddresses("localhost:11200 localhost:11201"));
		while(true) {
			for(int i=0; i<1000; i++) {
				try {
					c.getBulk("blah1", "blah2", "blah3", "blah4", "blah5");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("Did a thousand.");
		}
	}
