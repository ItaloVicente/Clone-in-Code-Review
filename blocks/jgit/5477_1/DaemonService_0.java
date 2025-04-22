		Repository db;
		try {
			db = client.getDaemon().openRepository(client
		} catch (ServiceMayNotContinueException e) {
			PacketLineOut pktOut = new PacketLineOut(client.getOutputStream());
			pktOut.writeString("ERR " + e.getMessage() + "\n");
			db = null;
		}
