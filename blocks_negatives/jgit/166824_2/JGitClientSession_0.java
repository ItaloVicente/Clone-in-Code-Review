
	/**
	 * Default setting for the maximum number of bytes to read in the initial
	 * protocol version exchange. 64kb is what OpenSSH < 8.0 read; OpenSSH 8.0
	 * changed it to 8Mb, but that seems excessive for the purpose stated in RFC
	 * 4253. The Apache MINA sshd default in
	 * {@link FactoryManager#DEFAULT_MAX_IDENTIFICATION_SIZE} is 16kb.
	 */
	private static final int DEFAULT_MAX_IDENTIFICATION_SIZE = 64 * 1024;

