		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n",
			PacketLineIn.delimiter(),
			"sideband-all\n",
			"want " + fooChild.toObjectId().getName() + "\n",
			"want " + barChild.toObjectId().getName() + "\n",
			"have " + fooParent.toObjectId().getName() + "\n",
			PacketLineIn.end());
