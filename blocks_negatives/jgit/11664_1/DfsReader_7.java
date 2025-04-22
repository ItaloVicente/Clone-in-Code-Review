
		try {
			wantReadAhead = true;
			for (ObjectToPack otp : list)
				out.writeObject(otp);
		} finally {
			cancelReadAhead();
		}
