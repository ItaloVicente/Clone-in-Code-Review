			final int cc;
			if (stIdx < childCnt) {
				cc = namecmp(currPath
				if (cc > 0) {
					removeChild(stIdx);
					continue;
				}
			} else {
				cc = -1;
