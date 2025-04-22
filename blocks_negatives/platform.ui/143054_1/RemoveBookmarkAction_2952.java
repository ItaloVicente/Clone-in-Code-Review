        IMarker[] markers = new IMarker[list.size()];
        list.toArray(markers);
     	IUndoableOperation op = new DeleteMarkersOperation(markers, BookmarkMessages.RemoveBookmark_undoText);
   		execute(op, BookmarkMessages.RemoveBookmark_errorTitle, null,
   				WorkspaceUndoUtil.getUIInfoAdapter(getView().getShell()));
    }
