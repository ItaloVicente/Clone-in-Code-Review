		private int setInput(long pos
			if (pos < currPos)
				return getOrLoadBlock(pos).setInput(pos
			if (pos < currPos + currPtr) {
				int s = (int) (pos - currPos);
				int n = currPtr - s;
				inf.setInput(currBuf
				return n;
