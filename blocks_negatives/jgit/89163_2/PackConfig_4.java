  name = 'jgit',
  srcs = SRCS,
  resources = RESOURCES,
  resource_strip_prefix = 'org.eclipse.jgit/resources',
  deps = [
    ':insecure_cipher_factory',
  ],
