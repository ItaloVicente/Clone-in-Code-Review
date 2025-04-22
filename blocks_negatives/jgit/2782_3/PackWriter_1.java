		int nWeight;
		if (otp.isReuseAsIs()) {
			if (PACK_WHOLE < nFmt)
			else if (PACK_DELTA < nFmt && otp.isDeltaRepresentation())

			nWeight = next.getWeight();
			if (otp.getWeight() <= nWeight)
		} else
			nWeight = next.getWeight();

