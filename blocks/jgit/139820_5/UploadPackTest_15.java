		ByteArrayInputStream recvStream = uploadPackV2("command=fetch\n"
				PacketLineIn.delimiter()
				"shallow " + three.toObjectId().getName() + "\n"
				"deepen-not side\n"
				"want " + merge.toObjectId().getName() + "\n"
				"have " + three.toObjectId().getName() + "\n"
