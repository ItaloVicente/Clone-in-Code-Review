		for (ObjectToPack otp : objectsLists[type]) {
			ObjectToPack base = otp.getDeltaBase();
			if (base != null)
				base.setDoNotDelta(true);
		}

