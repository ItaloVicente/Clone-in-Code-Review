  @SuppressWarnings("unchecked")
  public <D extends Document<?>> D decode(final CoreDocument coreDocument) {
    if (coreDocument.status() != ResponseStatus.SUCCESS)
    {
      return (D) JsonDocument.create(coreDocument.id(), null, coreDocument.cas(), coreDocument.expiration(), coreDocument.status());
    }

