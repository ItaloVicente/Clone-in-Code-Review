			try {
				MessageDigest md = newMD5();
				md.update((byte) ':');
				return LHEX(md.digest());
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("UTF-8 encoding not available", e); //$NON-NLS-1$
			}
