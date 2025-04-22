  private void nextHash() {
    long tmpKey = hashAlg.hash((numTries++) + key);
    hashVal += (int) (tmpKey ^ (tmpKey >>> 32));
    hashVal &= 0xffffffffL; /* truncate to 32-bits */
    remainingTries--;
  }
