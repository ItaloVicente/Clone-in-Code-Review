    if (!configuration.equals(bucket.configuration)) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + configuration.hashCode();
    result = 31 * result + nodes.hashCode();
    return result;
  }
