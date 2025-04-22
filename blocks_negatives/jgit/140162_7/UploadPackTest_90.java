		recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want " + barChild.toObjectId().getName() + "\n",
			"have " + fooChild.toObjectId().getName() + "\n",
			"shallow " + fooChild.toObjectId().getName() + "\n",
			"done\n",
			PacketLineIn.END);
