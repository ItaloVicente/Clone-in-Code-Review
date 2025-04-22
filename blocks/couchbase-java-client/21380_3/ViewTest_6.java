
  @Test
  public void testDesignDocumentCreation() {
    DesignDocument doc = new DesignDocument();
    doc.setName("mydesign");

    HttpFuture<String> result;
    boolean success = true;
    try {
      result = client.asyncCreateDesignDoc(doc);
      Object foo = result.get();
      System.out.println(foo);
      assertEquals("bar", foo);

      assertTrue(result.getStatus().isSuccess());
      assertEquals("Error Code: 201", result.getStatus().getMessage());
    } catch (Exception ex) {
      success = false;
    }
    assertTrue(success);

  }
