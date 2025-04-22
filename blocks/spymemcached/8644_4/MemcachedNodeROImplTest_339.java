  private void fillArgs(Class<?>[] parameterTypes, Object[] args) {
    int i = 0;
    for (Class<?> c : parameterTypes) {
      if (c == Boolean.TYPE) {
        args[i++] = false;
      } else {
        args[i++] = null;
      }
    }
  }
