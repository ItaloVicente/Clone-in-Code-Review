	public static void main(String[] args) throws Exception {

		if (args.length != 4) {
            args = new String[]{"1000", "100", "11211", "100"};
			System.out.println("Usage: java "
					+ MemcachedThreadBench.class.getName()
					+ " <runs> <start> <port> <threads>");
