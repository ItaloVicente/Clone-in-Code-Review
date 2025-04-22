  public void testOverridingExecutorService() {
    ConnectionFactory factory = b.build();
    assertTrue(factory.isDefaultExecutorService());

    ExecutorService service = Executors.newFixedThreadPool(1);
    b.setListenerExecutorService(service);
    factory = b.build();
    assertFalse(factory.isDefaultExecutorService());
    assertEquals(service.hashCode(), factory.getListenerExecutorService().hashCode());
  }

