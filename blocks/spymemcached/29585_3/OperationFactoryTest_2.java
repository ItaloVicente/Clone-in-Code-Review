
  public void testGetAndTouchOperationCloning() {
    GetAndTouchOperation.Callback callback =
      (GetAndTouchOperation.Callback) mock(GetAndTouchOperation.Callback.class).proxy();
    GetAndTouchOperation op = ofact.getAndTouch(TEST_KEY, 0, callback);

    GetAndTouchOperation op2 = cloneOne(GetAndTouchOperation.class, op);
    assertKey(op2);
    assertSame(callback, op2.getCallback());
  }

