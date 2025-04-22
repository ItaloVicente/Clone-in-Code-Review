		if (!validate) {
			ctx.unpin();
			return null;
		}

		int hdrlen = 12;
		byte[] buf = out.getCopyBuffer();
		int n = ctx.copy(this, 0, buf, 0, hdrlen);
		ctx.unpin();
		if (n != hdrlen)
			throw packfileIsTruncated();
		MessageDigest md = Constants.newMessageDigest();
		md.update(buf, 0, hdrlen);
		return md;
