  public void testTouchOperationCloning() {
    OperationCallback callback =
      (OperationCallback) mock(OperationCallback.class).proxy();

    TouchOperation op = ofact.touch(TEST_KEY, 0, callback);

    TouchOperation op2 = cloneOne(TouchOperation.class, op);
    assertKey(op2);
    assertSame(callback, op2.getCallback());
  }

