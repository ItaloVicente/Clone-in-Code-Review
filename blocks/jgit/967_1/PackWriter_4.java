
	public void select(ObjectToPack otp
		int nFmt = next.getFormat();
		int nWeight;
		if (otp.isReuseAsIs()) {
			if (PACK_WHOLE < nFmt)
			else if (PACK_DELTA < nFmt && otp.isDeltaRepresentation())

			nWeight = next.getWeight();
			if (otp.getWeight() <= nWeight)
		} else
			nWeight = next.getWeight();

		if (nFmt == PACK_DELTA && reuseDeltas) {
			ObjectId baseId = next.getDeltaBase();
			ObjectToPack ptr = objectsMap.get(baseId);
			if (ptr != null) {
				otp.setDeltaBase(ptr);
				otp.setReuseAsIs();
				otp.setWeight(nWeight);
			} else if (thin && edgeObjects.contains(baseId)) {
				otp.setDeltaBase(baseId);
				otp.setReuseAsIs();
				otp.setWeight(nWeight);
			} else {
				otp.clearDeltaBase();
				otp.clearReuseAsIs();
			}
		} else if (nFmt == PACK_WHOLE && reuseObjects) {
			otp.clearDeltaBase();
			otp.setReuseAsIs();
			otp.setWeight(nWeight);
		} else {
			otp.clearDeltaBase();
			otp.clearReuseAsIs();
		}

		otp.select(next);
	}
