      System.out.println("old: " + oldList);
      System.out.println("new: " + newList);
      int nIndex1 = newList.indexOf(new URI("http://127.0.0.1:8091/pools"));
      int nIndex2 = newList.indexOf(new URI("http://127.0.0.2:8091/pools"));
      int nIndex3 = newList.indexOf(new URI("http://127.0.0.3:8091/pools"));
      int nIndex4 = newList.indexOf(new URI("http://127.0.0.5:8091/pools"));
      if (oIndex1 != nIndex1 || oIndex2 != nIndex2 || oIndex3 != nIndex3 || oIndex4 == nIndex4) {
