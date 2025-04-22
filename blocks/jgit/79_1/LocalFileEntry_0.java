
  public boolean isExecutableSupported() {
    return LocalFileSystem.platform.isExecutableSupproted();
  }

  public boolean isExecutable() {
    if (LocalFileSystem.platform.isExecutableSupproted()) {
      try {
        final Object r = LocalFileSystem.canExecute.invoke(
                getLocalFile()
                (Object[]) null);
        return ((Boolean) r).booleanValue();
      }
      catch (IllegalArgumentException e) {
        throw new Error(e);
      }
      catch (IllegalAccessException e) {
        throw new Error(e);
      }
      catch (InvocationTargetException e) {
        throw new Error(e);
      }
    }
    else {
      return false;
    }
  }

  public boolean setExecutable(boolean executable) {
    try {
      final Object r;
      r = LocalFileSystem.setExecute.invoke(getLocalFile()
                Boolean.valueOf(executable)});
      return ((Boolean) r).booleanValue();
    }
    catch (IllegalArgumentException e) {
      throw new Error(e);
    }
    catch (IllegalAccessException e) {
      throw new Error(e);
    }
    catch (InvocationTargetException e) {
      throw new Error(e);
    }
  }
