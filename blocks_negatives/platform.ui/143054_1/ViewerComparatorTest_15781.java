	        switch (change.getKind()) {
	        case TestModelChange.INSERT:
	            doInsert(change);
	            break;
	        case TestModelChange.REMOVE:
	            doRemove(change);
	            break;
	        case TestModelChange.STRUCTURE_CHANGE:
	            doStructureChange(change);
	            break;
	        case TestModelChange.NON_STRUCTURE_CHANGE:
	            doNonStructureChange(change);
	            break;
	        default:
	            throw new IllegalArgumentException("Unknown kind of change");
	        }

	        StructuredSelection selection = new StructuredSelection(change
	                .getChildren());
	        if ((change.getModifiers() & TestModelChange.SELECT) != 0) {
	            fViewer.setSelection(selection);
	        }
	        if ((change.getModifiers() & TestModelChange.REVEAL) != 0) {
	            Object element = selection.getFirstElement();
	            if (element != null) {
	                fViewer.reveal(element);
	            }
	        }
	    }

	    protected void doInsert(ComparatorModelChange change) {
	        if (fViewer instanceof ListViewer) {
	            if (change.getParent() != null
	                    && change.getParent().equals(fViewer.getInput())) {
	                ((ListViewer) fViewer).add(change.getChildren());
	            }
	        } else if (fViewer instanceof TableViewer) {
	            if (change.getParent() != null
	                    && change.getParent().equals(fViewer.getInput())) {
	                ((TableViewer) fViewer).add(change.getChildren());
	            }
	        } else if (fViewer instanceof AbstractTreeViewer) {
	            ((AbstractTreeViewer) fViewer).add(change.getParent(), change
	                    .getChildren());
	        } else if (fViewer instanceof ComboViewer) {
	            ((ComboViewer) fViewer).add(change.getChildren());
	        } else {
	            Assert.isTrue(false, "Unknown kind of viewer");
	        }
	    }

	    protected void doNonStructureChange(ComparatorModelChange change) {
           fViewer.update(change.getParent(),
                    new String[] { IBasicPropertyConstants.P_TEXT });
	    }

	    protected void doRemove(ComparatorModelChange change) {
	        if (fViewer instanceof ListViewer) {
	            ((ListViewer) fViewer).remove(change.getChildren());
	        } else if (fViewer instanceof TableViewer) {
	            ((TableViewer) fViewer).remove(change.getChildren());
	        } else if (fViewer instanceof AbstractTreeViewer) {
	            ((AbstractTreeViewer) fViewer).remove(change.getChildren());
	        } else if (fViewer instanceof ComboViewer) {
	            ((ComboViewer) fViewer).remove(change.getChildren());
	        } else {
	            Assert.isTrue(false, "Unknown kind of viewer");
	        }
	    }

	    protected void doStructureChange(ComparatorModelChange change) {
            fViewer.refresh(change.getParent());
	    }
