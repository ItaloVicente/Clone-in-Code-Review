		for (;;) {
			PackedObjectLoader reuse = otp.useLoader();
			if (reuse == null) {
				return null;
			}

