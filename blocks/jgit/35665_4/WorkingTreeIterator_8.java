			AutoCRLF autoCRLF = getOptions().getAutoCRLF();
			boolean hasIdentSet = hasIdentSet(attrs);
			if (autoCRLF == AutoCRLF.FALSE && !hasIdentSet) {
				return true;
			}

			InputStream dcIn = null;
			try {
				ObjectLoader loader = reader.open(entry.getObjectId());
				if (loader == null)
					return true;

				dcIn = loader.openStream();
				if (autoCRLF == AutoCRLF.TRUE || autoCRLF == AutoCRLF.INPUT) {
					dcIn = new EolCanonicalizingInputStream(dcIn
				}

				if (hasIdentSet) {
					dcIn = new IdentInputStream(dcIn
				}

				long dcInLen;
				try {
					dcInLen = computeLength(dcIn);
				} catch (EolCanonicalizingInputStream.IsBinaryException e) {
