        if (sourcePart != null) {
        	sourcePart.getSite().getPage().removePartListener(partListener);
        	sourcePart = null;
        }

        if (selection instanceof IStructuredSelection) {
        	sourcePart = part;
            viewer.setInput(((IStructuredSelection) selection).toArray());
        }

        if (sourcePart != null) {
        	sourcePart.getSite().getPage().addPartListener(partListener);
        }
    }
