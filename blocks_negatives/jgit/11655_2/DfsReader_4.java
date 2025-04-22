			if (!found)
				throw new MissingObjectException(otp, otp.getType());
			if ((++updated & 1) == 1) {
				posted++;
			}
			objectCount++;
