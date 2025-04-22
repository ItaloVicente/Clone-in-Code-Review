    		toolbarContributionItemDisposed();
    		super.dispose();
    	}
    };
    
    private class DummyAction extends Action {
    	public DummyAction() {
    		setText("Monkey");
			setImageDescriptor(getViewSite().getWorkbenchWindow()
					.getWorkbench().getSharedImages()
					.getImageDescriptor(
							ISharedImages.IMG_TOOL_DELETE));
		}    	
    }
    
    public MockViewPart() {
        super();
    }
