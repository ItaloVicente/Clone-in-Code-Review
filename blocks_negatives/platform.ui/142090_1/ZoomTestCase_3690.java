        } catch (PartInitException e) {
        }
    }

    protected IViewPart showRegularView(String id, int mode) {
        try {
            IViewPart view = page.showView(id, null, mode);
            return view;
        } catch (PartInitException e) {
        }
        return null;
    }

    protected IViewPart findView(String id) {
        IViewPart view = page.findView(id);
        assertNotNull("View " + id + " not found", view);
        return view;
    }

    protected MPart getPartModel(IWorkbenchPart part) {
        PartSite site = (PartSite) part.getSite();
        return site.getModel();
    }

    protected MUIElement getPartParent(IWorkbenchPart part) {
        MPart partModel = getPartModel(part);

        MUIElement partParent = partModel.getParent();
        if (partParent == null && partModel.getCurSharedRef() != null) {
