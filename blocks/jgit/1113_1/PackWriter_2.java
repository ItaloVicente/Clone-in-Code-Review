	private void searchForDeltas(ProgressMonitor monitor)
			throws MissingObjectException
			IOException {
		ObjectToPack[] list = new ObjectToPack[
				  objectsLists[Constants.OBJ_TREE].size()
				+ objectsLists[Constants.OBJ_BLOB].size()
				+ edgeObjects.size()];
		int cnt = 0;
		cnt = findObjectsNeedingDelta(list
		cnt = findObjectsNeedingDelta(list
		if (cnt == 0)
			return;

		for (ObjectToPack eo : edgeObjects) {
			try {
				if (loadSize(eo))
					list[cnt++] = eo;
			} catch (IOException notAvailable) {
				if (!ignoreMissingUninteresting)
					throw notAvailable;
			}
		}

		monitor.beginTask(COMPRESSING_OBJECTS_PROGRESS

		Arrays.sort(list
			public int compare(ObjectToPack a
				int cmp = a.getType() - b.getType();
				if (cmp == 0)
					cmp = (a.getPathHash() >>> 1) - (b.getPathHash() >>> 1);
				if (cmp == 0)
					cmp = (a.getPathHash() & 1) - (b.getPathHash() & 1);
				if (cmp == 0)
					cmp = b.getWeight() - a.getWeight();
				return cmp;
			}
		});
		searchForDeltas(monitor
		monitor.endTask();
	}

	private int findObjectsNeedingDelta(ObjectToPack[] list
			throws MissingObjectException
			IOException {
		for (ObjectToPack otp : objectsLists[type]) {
				continue;
				continue;
			if (loadSize(otp))
				list[cnt++] = otp;
		}
		return cnt;
	}

	private boolean loadSize(ObjectToPack e) throws MissingObjectException
			IncorrectObjectTypeException
		long sz = reader.getObjectSize(e

		if (bigFileThreshold <= sz || Integer.MAX_VALUE <= sz)
			return false;

		if (sz <= DeltaIndex.BLKSZ)
			return false;

		e.setWeight((int) sz);
		return true;
	}

	private void searchForDeltas(ProgressMonitor monitor
			ObjectToPack[] list
			IncorrectObjectTypeException
		DeltaWindow dw = new DeltaWindow(this
		dw.search(monitor
	}

