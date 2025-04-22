   * Returns a uniquely identifying key, suitable for hashing by the
   * KetamaNodeLocator algorithm.
   *
   * <p>
   * This default implementation uses the socket-address of the MemcachedNode
   * and concatenates it with a hyphen directly against the repetition number
   * for example a key for a particular server's first repetition may look like:
   * <p>
   *
   * <p>
   * <code>myhost/10.0.2.1-0</code>
   * </p>
   *
   * <p>
   * for the second repetition
   * </p>
   *
   * <p>
   * <code>myhost/10.0.2.1-1</code>
   * </p>
   *
   * <p>
   * for a server where reverse lookups are failing the returned keys may look
   * like
   * </p>
   *
   * <p>
   * <code>/10.0.2.1-0</code> and <code>/10.0.2.1-1</code>
   * </p>
   *
   * @param node The MemcachedNode to use to form the unique identifier
   * @param repetition The repetition number for the particular node in question
   *          (0 is the first repetition)
   * @return The key that represents the specific repetition of the node
