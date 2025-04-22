        sortByResourceAction = new SortByAction(BookmarkSorter.RESOURCE);
        sortByResourceAction.setText(BookmarkMessages.ColumnResource_text);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(sortByResourceAction,
                IBookmarkHelpContextIds.SORT_RESOURCE_ACTION);

        sortByFolderAction = new SortByAction(BookmarkSorter.FOLDER);
        sortByFolderAction.setText(BookmarkMessages.ColumnFolder_text);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(sortByFolderAction,
                IBookmarkHelpContextIds.SORT_FOLDER_ACTION);

        sortByLineAction = new SortByAction(BookmarkSorter.LOCATION);
        sortByLineAction.setText(BookmarkMessages.ColumnLocation_text);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(sortByLineAction,
                IBookmarkHelpContextIds.SORT_LOCATION_ACTION);

        sortByCreationTime = new SortByAction(BookmarkSorter.CREATION_TIME);
        sortByCreationTime.setText(BookmarkMessages.ColumnCreationTime_text);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(sortByCreationTime,
                IBookmarkHelpContextIds.SORT_CREATION_TIME_ACTION);

        sortAscendingAction = new ChangeSortDirectionAction(
                BookmarkSorter.ASCENDING);
        sortAscendingAction.setText(BookmarkMessages.SortDirectionAscending_text);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(sortAscendingAction,
                IBookmarkHelpContextIds.SORT_ASCENDING_ACTION);

        sortDescendingAction = new ChangeSortDirectionAction(
                BookmarkSorter.DESCENDING);
        sortDescendingAction.setText(BookmarkMessages.SortDirectionDescending_text);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(sortDescendingAction,
                IBookmarkHelpContextIds.SORT_DESCENDING_ACTION);
    }

    void updateSortState() {
        int column = comparator.getTopPriority();
        sortByDescriptionAction
                .setChecked(column == BookmarkSorter.DESCRIPTION);
        sortByResourceAction.setChecked(column == BookmarkSorter.RESOURCE);
        sortByFolderAction.setChecked(column == BookmarkSorter.FOLDER);
        sortByLineAction.setChecked(column == BookmarkSorter.LOCATION);
        sortByCreationTime.setChecked(column == BookmarkSorter.CREATION_TIME);

        int direction = comparator.getTopPriorityDirection();
        sortAscendingAction.setChecked(direction == BookmarkSorter.ASCENDING);
        sortDescendingAction.setChecked(direction == BookmarkSorter.DESCENDING);
    }

    /**
     * Updates the enablement of the paste action
     */
    void updatePasteEnablement() {
        MarkerTransfer transfer = MarkerTransfer.getInstance();
        IMarker[] markerData = (IMarker[]) getClipboard().getContents(transfer);
        boolean canPaste = false;
        if (markerData != null) {
            for (IMarker marker : markerData) {
                try {
                    if (marker.getType().equals(IMarker.BOOKMARK)) {
                        canPaste = true;
                        break;
                    }
                } catch (CoreException e) {
                    canPaste = false;
                }
            }
        }
        pasteAction.setEnabled(canPaste);
    }

    Clipboard getClipboard() {
        return clipboard;
    }
