			try {
				wantReadAhead = true;
				PackReverseIndex rev = pack.getReverseIdx(this);
				DfsObjectRepresentation rep = new DfsObjectRepresentation(
						pack,
						packIndex);
				for (DfsObjectToPack otp : tmp) {
					pack.representation(rep, otp.getOffset(), this, rev);
					otp.setOffset(0);
					packer.select(otp, rep);
					if (!otp.isFound()) {
						otp.setFound();
						monitor.update(1);
					}
