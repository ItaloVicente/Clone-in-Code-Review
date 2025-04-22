	private ConnectivityCheckInfo createConnectivityCheckInfo() {
		ConnectivityCheckInfo info = new ConnectivityCheckInfo();
		info.setCheckObjects(checkReferencedAreReachable);
		info.setCommands(getAllCommands());
		info.setRepository(db);
		info.setParser(parser);
		info.setWalk(walk);
		return info;
