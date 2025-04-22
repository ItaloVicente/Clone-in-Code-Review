			switch (getOptions().getAutoCRLF()) {
			case INPUT:
			case TRUE:
				InputStream dcIn = null;
				try {
					ObjectLoader loader = reader.open(entry.getObjectId());
					if (loader == null)
						return true;

					dcIn = new EolCanonicalizingInputStream(
							loader.openStream(), true, true /* abort if binary */);
					long dcInLen;
					try {
						dcInLen = computeLength(dcIn);
					} catch (EolCanonicalizingInputStream.IsBinaryException e) {
						return true;
					} finally {
						dcIn.close();
					}
