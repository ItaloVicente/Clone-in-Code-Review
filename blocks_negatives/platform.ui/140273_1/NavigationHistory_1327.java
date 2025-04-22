            return;
        }

        IMemento children[] = editorsMem
                .getChildren(IWorkbenchConstants.TAG_EDITOR);
        NavigationHistoryEditorInfo editorsInfo[] = new NavigationHistoryEditorInfo[children.length];
        for (int i = 0; i < editorsInfo.length; i++) {
            editorsInfo[i] = new NavigationHistoryEditorInfo(children[i]);
            editors.add(editorsInfo[i]);
        }

        for (int i = 0; i < items.length; i++) {
            IMemento item = items[i];
            int index = item.getInteger(IWorkbenchConstants.TAG_INDEX)
                    .intValue();
            NavigationHistoryEditorInfo info = editorsInfo[index];
            info.refCount++;
            NavigationHistoryEntry entry = new NavigationHistoryEntry(info,
                    page, null, null);
            history.add(entry);
            entry.restoreState(item);
            if (item.getString(IWorkbenchConstants.TAG_ACTIVE) != null) {
