			long p = packLast.findOffset(this, otp);
			if (p < 0) {
				int skip = packIndex;
				for (packIndex = 0; packIndex < packList.length; packIndex++) {
					if (skip == packIndex)
						continue;
					packLast = packList[packIndex];
					p = packLast.findOffset(this, otp);
					if (0 < p)
						break;
