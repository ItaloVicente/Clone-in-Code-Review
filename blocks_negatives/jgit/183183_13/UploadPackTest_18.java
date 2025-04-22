		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want " + barChild.toObjectId().getName() + "\n",
			"have " + fooChild.toObjectId().getName() + "\n",
			"done\n",
