	        if (comparatorTestViewer instanceof ListViewer) {
	            ((ListViewer<Object,Object>) comparatorTestViewer).remove(change.getChildren());
	        } else if (comparatorTestViewer instanceof TableViewer) {
	            ((TableViewer<Object,Object>) comparatorTestViewer).remove(change.getChildren());
	        } else if (comparatorTestViewer instanceof AbstractTreeViewer) {
	            ((AbstractTreeViewer<Object,Object>) comparatorTestViewer).remove(change.getChildren());
	        } else if (comparatorTestViewer instanceof ComboViewer) {
	            ((ComboViewer<Object,Object>) comparatorTestViewer).remove(change.getChildren());
