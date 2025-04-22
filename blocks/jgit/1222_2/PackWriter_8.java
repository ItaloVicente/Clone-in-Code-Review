		AsyncObjectSizeQueue<ObjectToPack> sizeQueue = reader.getObjectSize(
				Arrays.<ObjectToPack> asList(list).subList(0
		try {
			final long limit = config.getBigFileThreshold();
			for (;;) {
				monitor.update(1);

				try {
					if (!sizeQueue.next())
						break;
				} catch (MissingObjectException notFound) {
					if (ignoreMissingUninteresting) {
						ObjectToPack otp = sizeQueue.getCurrent();
						if (otp != null && otp.isEdge()) {
							otp.setDoNotDelta(true);
							continue;
						}

						otp = edgeObjects.get(notFound.getObjectId());
						if (otp != null) {
							otp.setDoNotDelta(true);
							continue;
						}
					}
					throw notFound;
				}

				ObjectToPack otp = sizeQueue.getCurrent();
				if (otp == null) {
					otp = objectsMap.get(sizeQueue.getObjectId());
					if (otp == null)
						otp = edgeObjects.get(sizeQueue.getObjectId());
				}

				long sz = sizeQueue.getSize();
				if (limit <= sz || Integer.MAX_VALUE <= sz)

				else if (sz <= DeltaIndex.BLKSZ)

				else
					otp.setWeight((int) sz);
			}
		} finally {
			sizeQueue.release();
		}
		monitor.endTask();
