			int ancestorNth = -1;
			if (!ancestorId.equals(zeroId()))
				ancestorNth = tw.addTree(ancestorId);

			int baseNth = -1;
			if (!baseId.equals(zeroId()))
				baseNth = tw.addTree(baseId);

			int remoteNth = -1;
			if (!remoteId.equals(zeroId()))
				remoteNth = tw.addTree(remoteId);
