		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"want " + fooChild.toObjectId().getName() + "\n",
			"want " + barChild.toObjectId().getName() + "\n",
			"have " + fooParent.toObjectId().getName() + "\n",
