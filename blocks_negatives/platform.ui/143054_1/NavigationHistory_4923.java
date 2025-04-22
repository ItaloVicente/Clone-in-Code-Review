                    boolean isEntryDisposed = false;
                    e = history.iterator();
                    int i = 0;
                    while (e.hasNext()) {
                        NavigationHistoryEntry entry = (NavigationHistoryEntry) e
                                .next();
                        if (entry.editorInfo == info) {
