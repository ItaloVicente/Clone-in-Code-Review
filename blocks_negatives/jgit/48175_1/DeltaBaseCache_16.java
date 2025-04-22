			Slot currOldest = lruTail;
			Slot nextOldest = currOldest.lruPrev;

			curByteCount -= currOldest.size;
			unlink(currOldest);
			removeFromTable(currOldest);

			if (nextOldest == null)
				lruHead = null;
			else
				nextOldest.lruNext = null;
			lruTail = nextOldest;
