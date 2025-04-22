	private static void sendPublishSubscribeError(HttpServletRequest req
			HttpServletResponse res
		ByteArrayOutputStream buf = new ByteArrayOutputStream(128);
		PacketLineOut pckOut = new PacketLineOut(buf);
		writePacket(pckOut
		send(req
	}

