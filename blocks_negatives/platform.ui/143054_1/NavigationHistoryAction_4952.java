        NavigationHistoryEntry previousEntry = null;
        int i = -1;
        for (Iterator<NavigationHistoryEntry> iter = allEntries.iterator(); iter.hasNext();) {
            NavigationHistoryEntry entry = iter.next();
            if (previousEntry != null) {
                String text = previousEntry.getHistoryText();
                if (text != null) {
                    if (text.equals(entry.getHistoryText())
                            && previousEntry.editorInfo == entry.editorInfo) {
                        iter.remove();
                        entriesCount[i]++;
                        continue;
                    }
                }
            }
            previousEntry = entry;
            i++;
        }
        entries = new NavigationHistoryEntry[allEntries.size()];
        return allEntries.toArray(entries);
    }
