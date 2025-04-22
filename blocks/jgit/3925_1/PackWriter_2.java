		if (list.get(0).getType() == Constants.OBJ_BLOB) {
			for (ObjectToPack otp : list) {
				if (otp.isWritten() || otp.getDeltaBase() != null)
					continue;
				writeObjectAndDeltaChildren(out
			}
		} else if (reuseSupport != null) {
