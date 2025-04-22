	/**
	 * Compute the hash for the given key.
	 *
	 * @return a positive integer hash
	 */
	public long hash(final String k) {
		long rv = 0;
		switch (this) {
			case NATIVE_HASH:
				rv = k.hashCode();
				break;
			case CRC32_HASH:
				CRC32 crc32 = new CRC32();
				crc32.update(KeyUtil.getKeyBytes(k));
				rv = (crc32.getValue() >> 16) & 0x7fff;
				break;
			case FNV1_64_HASH: {
					rv = FNV_64_INIT;
					int len = k.length();
					for (int i = 0; i < len; i++) {
						rv *= FNV_64_PRIME;
						rv ^= k.charAt(i);
					}
				}
				break;
			case FNV1A_64_HASH: {
					rv = FNV_64_INIT;
					int len = k.length();
					for (int i = 0; i < len; i++) {
						rv ^= k.charAt(i);
						rv *= FNV_64_PRIME;
					}
				}
				break;
			case FNV1_32_HASH: {
					rv = FNV_32_INIT;
					int len = k.length();
					for (int i = 0; i < len; i++) {
						rv *= FNV_32_PRIME;
						rv ^= k.charAt(i);
					}
				}
				break;
			case FNV1A_32_HASH: {
					rv = FNV_32_INIT;
					int len = k.length();
					for (int i = 0; i < len; i++) {
						rv ^= k.charAt(i);
						rv *= FNV_32_PRIME;
					}
				}
				break;
			case KETAMA_HASH:
				byte[] bKey=computeMd5(k);
				rv = ((long) (bKey[3] & 0xFF) << 24)
						| ((long) (bKey[2] & 0xFF) << 16)
						| ((long) (bKey[1] & 0xFF) << 8)
						| (bKey[0] & 0xFF);
				break;
			default:
				assert false;
		}
		return rv & 0xffffffffL; /* Truncate to 32-bits */
	}
