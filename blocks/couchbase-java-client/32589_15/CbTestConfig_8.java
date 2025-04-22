
  public static class Version {

    private final int major;
    private final int minor;
    private final int bugfix;

    public Version(String raw) {
      String[] tokens = raw.replaceAll("_.*$", "").split("\\.");
      major = Integer.parseInt(tokens[0]);
      minor = Integer.parseInt(tokens[1]);
      bugfix = Integer.parseInt(tokens[2]);
    }

    public boolean greaterOrEqualThan(int major, int minor, int bugfix) {
      return this.major >= major
        && this.minor >= minor
        && this.bugfix >= bugfix;
    }

    public boolean isCarrierConfigAware() {
      return greaterOrEqualThan(2, 5, 0);
    }
  }

