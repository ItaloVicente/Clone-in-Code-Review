  /**
   * Observe a key with a associated CAS.
   *
   * This method allows you to check immediately on the state of a given
   * key/CAS combination. It is normally used by higher-level methods when
   * used in combination with durability constraints (ReplicateTo,
   * PersistTo), but can also be used separately.
   *
   * @param key the key to observe.
   * @param cas the CAS of the key (0 will ignore it).
   * @return ObserveReponse the Response on master and replicas.
   * @throws IllegalStateException in the rare circumstance where queue is too
   *           full to accept any more requests.
   */
