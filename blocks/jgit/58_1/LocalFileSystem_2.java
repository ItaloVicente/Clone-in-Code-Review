
  public Entry resolve(Entry entry
                       String path) {
    if (!(entry instanceof LocalFileEntry)) {
      return null;
    }
    LocalFileEntry fileEntry = (LocalFileEntry) entry;
    File localFile = fileEntry.getLocalFile();
    if (platform.equals(Platform.WIN32_CYGWIN)) {
      try {
        final Process p;

        p = Runtime.getRuntime().exec(
                new String[] {cygpath
                null
        p.getOutputStream().close();

        final BufferedReader lineRead = new BufferedReader(
                new InputStreamReader(p.getInputStream()
        String r = null;
        try {
          r = lineRead.readLine();
        }
        finally {
          lineRead.close();
        }

        for (;;) {
          try {
            if (p.waitFor() == 0 && r != null && r.length() > 0) {
              return new LocalFileEntry(new File(r)
            }
            break;
          }
          catch (InterruptedException ie) {
          }
        }
      }
      catch (IOException ioe) {
      }

    }
    final File abspn = new File(path);
    if (abspn.isAbsolute()) {
      return new LocalFileEntry(abspn
    }
    return new LocalFileEntry(new File(localFile
  }

  public Entry getHomeDirectory() {
    if (platform.equals(Platform.WIN32_CYGWIN)) {
      final String home = AccessController.doPrivileged(new PrivilegedAction<String>() {

        public String run() {
          return System.getenv("HOME");
        }
      });
      if (!(home == null || home.length() == 0)) {
        return resolve(new LocalFileEntry(new File(".")
      }
    }
    final String home = AccessController.doPrivileged(new PrivilegedAction<String>() {

      public String run() {
        return System.getProperty("user.home");
      }
    });
    if (home == null || home.length() == 0) {
      return null;
    }
    return new LocalFileEntry(new File(home).getAbsoluteFile()
  }

  public enum Platform {

    WIN32_CYGWIN(false)
    WIN32(false)
    POSIX_JAVA5(false)
    POSIX_JAVA6(true);
    private boolean executableSupproted;

    private Platform(boolean executableSupproted) {
      this.executableSupproted = executableSupproted;
    }

    public boolean isExecutableSupproted() {
      return executableSupproted;
    }

    public static Platform detect() {
      final String osDotName = AccessController.doPrivileged(new PrivilegedAction<String>() {

        public String run() {
          return System.getProperty("os.name");
        }
      });
      if (osDotName != null &&
          osDotName.toLowerCase().indexOf("windows") != -1) {
        final String path = AccessController.doPrivileged(new PrivilegedAction<String>() {

          public String run() {
            return System.getProperty("java.library.path");
          }
        });
        if (path == null) {
          return WIN32;
        }
        for (final String p : path.split(";")) {
          final File e = new File(p
          if (e.isFile()) {
            cygpath = e.getAbsolutePath();
            return WIN32_CYGWIN;
          }
        }
        return WIN32;
      }
      else {
        canExecute = needMethod(File.class
        setExecute = needMethod(File.class
                Boolean.TYPE);
        if (canExecute != null && setExecute != null) {
          return POSIX_JAVA6;
        }
        else {
          return POSIX_JAVA5;
        }
      }
    }

    private static Method needMethod(final Class<?> on
                                     final String name
                                     final Class<?>... args) {
      try {
        return on.getMethod(name
      }
      catch (SecurityException e) {
        return null;
      }
      catch (NoSuchMethodException e) {
        return null;
      }
    }
  }
