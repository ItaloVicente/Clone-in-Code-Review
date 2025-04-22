	public void testSeparatorParsing() throws Exception {
		config("Host\tfirst\n" +
		       "\tHostName\tfirst.tld\n" +
		       "\n" +
		       "Host second\n" +
		       " HostName\tsecond.tld\n" +
		       "Host=third\n" +
		       "HostName=third.tld\n\n\n" +
		       "\t Host = fourth\n\n\n" +
		       " \t HostName\t=fourth.tld\n" +
		       "Host\t =     last\n" +
		       "HostName  \t    last.tld");
