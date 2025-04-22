  public void testQuerySetRangeStartComplexKey() throws Exception {

    for (int i = 2009; i<2013; i++) {
      for (int j = 1; j<13; j++) {
        for (int k = 1; k<32; k++) {
          client.add("date" + i + j + k, 600, generateDatedDoc(i, j, k));
        }
      }
    }

