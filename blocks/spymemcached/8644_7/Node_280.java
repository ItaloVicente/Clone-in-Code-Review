
    return true;
  }

  @Override
  public int hashCode() {
    int result = status != null ? status.hashCode() : 0;
    result = 31 * result + hostname.hashCode();
    result = 31 * result + ports.hashCode();
    return result;
  }
