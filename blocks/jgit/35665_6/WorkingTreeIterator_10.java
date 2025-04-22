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

				dcIn = filterClean(loader.openStream()

				long dcInLen;
				try {
					dcInLen = computeLength(dcIn);
				} catch (EolCanonicalizingInputStream.IsBinaryException e) {
