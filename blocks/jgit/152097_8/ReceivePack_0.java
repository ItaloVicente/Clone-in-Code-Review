	private ConnectivityCheckInfo createConnectivityCheckInfo() {
		ConnectivityCheckInfo info = new ConnectivityCheckInfo();
		info.setCheckReferencedObjectsAreReachable(checkReferencedAreReachable);
		info.setCommands(getAllCommands());
		info.setDb(db);
		info.setParser(parser);
		info.setWalk(walk);
		return info;
