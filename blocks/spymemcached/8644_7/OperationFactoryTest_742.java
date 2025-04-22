    MutatorOperation op2 = cloneOne(MutatorOperation.class, op);
    assertKey(op2);
    assertEquals(-1, op2.getExpiration());
    assertEquals(-1, op2.getDefault());
    assertEquals(by, op2.getBy());
    assertSame(Mutator.decr, op2.getType());
    assertCallback(op2);
  }
