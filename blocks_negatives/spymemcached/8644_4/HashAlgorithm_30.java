	/**
	 * Native hash (String.hashCode()).
	 */
	NATIVE_HASH,
	/**
	 * CRC32_HASH as used by the perl API. This will be more consistent both
	 * across multiple API users as well as java versions, but is mostly likely
	 * significantly slower.
	 */
	CRC32_HASH,
	/**
	 * FNV hashes are designed to be fast while maintaining a low collision
	 * rate. The FNV speed allows one to quickly hash lots of data while
	 * maintaining a reasonable collision rate.
	 *
	 */
	FNV1_64_HASH,
	/**
	 * Variation of FNV.
	 */
	FNV1A_64_HASH,
	/**
	 * 32-bit FNV1.
	 */
	FNV1_32_HASH,
	/**
	 * 32-bit FNV1a.
	 */
	FNV1A_32_HASH,
	/**
	 * MD5-based hash algorithm used by ketama.
	 */
	KETAMA_HASH;
