	 * @throws Exception
	 *             deletion of test repository failed
	 * @throws IOException
	 *             deletion of test repository failed
	 */
	public void shutDown() throws Exception {
		src.getRepository().close();
		if (serveHttp)
			httpServer.stop();
		else
