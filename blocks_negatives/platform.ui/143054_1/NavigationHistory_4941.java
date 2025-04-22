					}});
			}
        }
    }

    public NavigationHistoryEntry createEntry(IWorkbenchPage page,
            IEditorPart part, INavigationLocation location) {
        String editorID = part.getSite().getId();
        IEditorInput editorInput = part.getEditorInput();
        NavigationHistoryEditorInfo info = null;
        for (Iterator iter = editors.iterator(); iter.hasNext();) {
            info = (NavigationHistoryEditorInfo) iter.next();
            if (editorID.equals(info.editorID)
                    && editorInput.equals(info.editorInput)) {
                info.refCount++;
                break;
            }
			info= null;
        }
        if (info == null) {
            info = new NavigationHistoryEditorInfo(part);
            info.refCount++;
            editors.add(info);
        }
        return new NavigationHistoryEntry(info, page, part, location);
    }

    public void disposeEntry(NavigationHistoryEntry entry) {
        if (entry.editorInfo == null) {
