
  @Test
  public void testDesignDocumentCreation() {
    DesignDocument doc = new DesignDocument();
    doc.setName("mydesign");

    String result = client.createDesignDoc(doc);
    assertEquals("foobar", result);

  }
