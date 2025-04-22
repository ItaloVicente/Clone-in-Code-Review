	private void serveHttp() throws Exception{
		httpServer = new SimpleHttpServer(src.getRepository());
		httpServer.start();
		uri = httpServer.getUri().toString();
	}
	
