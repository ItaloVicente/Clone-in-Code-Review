		ByteArrayInputStream recvStream = uploadPackV2("command=fetch\n"
				PacketLineIn.DELIM
				"shallow " + boundary.toObjectId().getName() + "\n"
				"deepen-since 1510000\n"
				"want " + merge.toObjectId().getName() + "\n"
				"have " + boundary.toObjectId().getName() + "\n"
				PacketLineIn.END);
