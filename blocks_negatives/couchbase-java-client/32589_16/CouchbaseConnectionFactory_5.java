  /**
   * Randomizes the entries of the node list if needed.
   *
   * @param list the list to potentially randomize.
   */
  private void potentiallyRandomizeNodeList(List<URI> list) {
    if (getStreamingNodeOrder().equals(CouchbaseNodeOrder.ORDERED)) {
      return;
    }

    Collections.shuffle(list);
  }

  /**
   * Check if two given node lists are different.
   *
   * @param left one node list
   * @param right the other node list
   * @return true if they are different, false otherwise.
   */
  private boolean nodeListsAreDifferent(List<URI> left, List<URI> right) {
    if (left.size() != right.size()) {
      return true;
    }

    for (URI uri : left) {
      if (!right.contains(uri)) {
        return true;
      }
    }
    return false;
  }

