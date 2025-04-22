  @Override
  protected void setUp() throws Exception {
    super.setUp();
    mutation = new CASMutation<Long>() {
      public Long getNewValue(Long current) {
        return current + 1;
      }
    };
    mutator = new CASMutator<Long>(client, new LongTranscoder(), 50);
  }
