public class ResourceTreeAndListGroup extends EventManager {
    
	private class CheckListener implements ICheckStateListener {
		public void checkStateChanged(final CheckStateChangedEvent event) {
	        BusyIndicator.showWhile(treeViewer.getControl().getDisplay(),
	                new Runnable() {
	                    public void run() {
	                        if (event.getCheckable().equals(treeViewer)) {
								treeItemChecked(event.getElement(), event
	                                    .getChecked());
							} else {
								listItemChecked(event.getElement(), event.getChecked(), true);
							}
	                        notifyCheckStateChangeListeners(event);
	                    }
	                });
		}
	}
	
	private class SelectionListener implements ISelectionChangedListener {
		public void selectionChanged(SelectionChangedEvent event) {
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
	        Object selectedElement = selection.getFirstElement();
	        if (selectedElement == null) {
	            currentTreeSelection = null;
	            listViewer.setInput(currentTreeSelection);
	            return;
	        }

	        if (selectedElement != currentTreeSelection) {
				populateListViewer(selectedElement);
			}
	        currentTreeSelection = selectedElement;
		}
		
	}
	
	private class TreeListener implements ITreeViewerListener {
		public void treeCollapsed(TreeExpansionEvent event) {
		}
