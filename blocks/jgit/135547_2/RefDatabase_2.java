  protected static final String[] SEARCH_PATH = {""
      Constants.R_REFS
      Constants.R_TAGS
      Constants.R_HEADS
  };

  public static final int MAX_SYMBOLIC_REF_DEPTH = 5;


  public abstract void create() throws IOException;

  public abstract void close();

  public boolean hasVersioning() {
    return false;
  }

  public abstract boolean isNameConflicting(String name) throws IOException;

  @NonNull
  public Collection<String> getConflictingNames(String name) throws IOException {
    Map<String
    int lastSlash = name.lastIndexOf('/');
    while (0 < lastSlash) {
      String needle = name.substring(0
      if (allRefs.containsKey(needle))
        return Collections.singletonList(needle);
      lastSlash = name.lastIndexOf('/'
    }

    List<String> conflicting = new ArrayList<>();
    String prefix = name + '/';
    for (String existing : allRefs.keySet())
      if (existing.startsWith(prefix))
        conflicting.add(existing);

    return conflicting;
  }

  @NonNull
  public abstract RefUpdate newUpdate(String name

  @NonNull
  public abstract RefRename newRename(String fromName

  @NonNull
  public BatchRefUpdate newBatchUpdate() {
    return new BatchRefUpdate(this);
  }

  public boolean performsAtomicTransactions() {
    return false;
  }

  @Deprecated
  @Nullable
  public final Ref getRef(String name) throws IOException {
    return findRef(name);
  }

  @Nullable
  public final Ref findRef(String name) throws IOException {
    String[] names = new String[SEARCH_PATH.length];
    for (int i = 0; i < SEARCH_PATH.length; i++) {
      names[i] = SEARCH_PATH[i] + name;
    }
    return firstExactRef(names);
  }

  @Nullable
  public abstract Ref exactRef(String name) throws IOException;

  @NonNull
  public Map<String
    Map<String
    for (String name : refs) {
      Ref ref = exactRef(name);
      if (ref != null) {
        result.put(name
      }
    }
    return result;
  }

  @Nullable
  public Ref firstExactRef(String... refs) throws IOException {
    for (String name : refs) {
      Ref ref = exactRef(name);
      if (ref != null) {
        return ref;
      }
    }
    return null;
  }

  @NonNull
  public List<Ref> getRefs() throws IOException {
    return getRefsByPrefix(ALL);
  }

  @NonNull
  @Deprecated
  public abstract Map<String

  @NonNull
  public List<Ref> getRefsByPrefix(String prefix) throws IOException {
    Map<String
    int lastSlash = prefix.lastIndexOf('/');
    if (lastSlash == -1) {
      coarseRefs = getRefs(ALL);
    } else {
      coarseRefs = getRefs(prefix.substring(0
    }

    List<Ref> result;
    if (lastSlash + 1 == prefix.length()) {
      result = coarseRefs.values().stream().collect(toList());
    } else {
      String p = prefix.substring(lastSlash + 1);
      result = coarseRefs.entrySet().stream().filter(e -> e.getKey().startsWith(p))
          .map(e -> e.getValue()).collect(toList());
    }
    return Collections.unmodifiableList(result);
  }

  @NonNull
  public List<Ref> getRefsByPrefix(String... prefixes) throws IOException {
    List<Ref> result = new ArrayList<>();
    for (String prefix : prefixes) {
      result.addAll(getRefsByPrefix(prefix));
    }
    return Collections.unmodifiableList(result);
  }


  @NonNull
  public Set<Ref> resolveTipSha1(ObjectId id) throws IOException {
    return getRefs().stream()
        .filter(r -> id.equals(r.getObjectId()) || id.equals(r.getPeeledObjectId()))
        .collect(Collectors.toSet());
  }

  public boolean hasRefs() throws IOException {
    return !getRefs().isEmpty();
  }

  @NonNull
  public abstract List<Ref> getAdditionalRefs() throws IOException;

  @NonNull
  public abstract Ref peel(Ref ref) throws IOException;

  public void refresh() {
  }

  @Nullable
  public static Ref findRef(Map<String
    for (String prefix : SEARCH_PATH) {
      String fullname = prefix + name;
      Ref ref = map.get(fullname);
      if (ref != null)
        return ref;
    }
    return null;
  }
