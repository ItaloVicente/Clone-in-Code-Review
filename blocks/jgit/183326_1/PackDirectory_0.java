  Collection<Pack> getPacks(boolean alwaysRefresh) {
    PackList list = packList.get();
    if (list == NO_PACKS || alwaysRefresh) {
      list = scanPacks(list);
    }
    Pack[] packs = list.packs;
    return Collections.unmodifiableCollection(Arrays.asList(packs));
  }

  Collection<Pack> getPacks() {
    return getPacks(false);
  }

  public void refreshPacks() {
    Collection<Pack> n = getPacks(true);
    PackList o = this.packList.get();
    this.packList.compareAndSet(
            o
  }
