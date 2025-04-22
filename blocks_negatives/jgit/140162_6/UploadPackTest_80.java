		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.DELIM,
			"want " + fooChild.toObjectId().getName() + "\n",
			"want " + barChild.toObjectId().getName() + "\n",
			"have " + fooParent.toObjectId().getName() + "\n",
			"have " + barParent.toObjectId().getName() + "\n",
			PacketLineIn.END);
