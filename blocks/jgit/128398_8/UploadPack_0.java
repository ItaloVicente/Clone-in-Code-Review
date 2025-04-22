	private void safePrintLine(PacketLineOut packetLineOut
		try {
			packetLineOut.writeString(line);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

