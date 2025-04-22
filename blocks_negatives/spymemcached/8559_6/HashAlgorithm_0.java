public enum HashAlgorithm {

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
   * FNV hashes are designed to be fast while maintaining a low collision rate.
   * The FNV speed allows one to quickly hash lots of data while maintaining a
   * reasonable collision rate.
   *
   *      comparisons</a>
   *      wikipedia</a>
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

  private static final long FNV_64_INIT = 0xcbf29ce484222325L;
  private static final long FNV_64_PRIME = 0x100000001b3L;

  private static final long FNV_32_INIT = 2166136261L;
  private static final long FNV_32_PRIME = 16777619;

  private static final MessageDigest MD5_DIGEST;

  static {
    try {
      MD5_DIGEST = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("MD5 not supported", e);
    }
  }
