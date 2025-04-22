  @Override
  public void initialize() {
    if (id != null) {
      message.setName(id);
    } else {
      message.setName(UUID.randomUUID().toString());
    }
    setBuffer(message.getBytes());
  }
