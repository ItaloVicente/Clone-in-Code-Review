		for (int packIndex = 0; packIndex < packList.length; packIndex++) {
			DfsPackFile pack = packList[packIndex];
			List<DfsObjectToPack> tmp = findAllFromPack(pack
			if (tmp.isEmpty())
				continue;
			Collections.sort(tmp
			try {
				wantReadAhead = true;
				PackReverseIndex rev = pack.getReverseIdx(this);
				DfsObjectRepresentation rep = new DfsObjectRepresentation(
						pack
						packIndex);
				for (DfsObjectToPack otp : tmp) {
					pack.representation(rep
					otp.setOffset(0);
					packer.select(otp
					if (!otp.isFound()) {
						otp.setFound();
						monitor.update(1);
					}
