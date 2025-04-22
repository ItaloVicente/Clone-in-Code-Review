  public void testE2BIG() throws Exception {
    client.add("bigSizeDoc", 0, "");
    int capacity = 10485761; // ~20MB
	StringBuilder sb = new StringBuilder(capacity);
    OperationStatus status = null;
    for (int i=0; i<=capacity; i++) {
      sb.append('a');
      status = client.append("bigSizeDoc", sb.toString())
                     .getStatus();
      if(!status.isSuccess()){
        break;
      }
    }
    assertEquals((status.getMessage()),"Too large");
  }

