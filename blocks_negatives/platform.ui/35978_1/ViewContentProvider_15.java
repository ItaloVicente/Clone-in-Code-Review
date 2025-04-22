    private ArrayList<Object> removeIntroView(ArrayList<Object> list) {
        for (Iterator<Object> i = list.iterator(); i.hasNext();) {
            IViewDescriptor view = (IViewDescriptor) i.next();
            if (view.getId().equals(IIntroConstants.INTRO_VIEW_ID)) {
                i.remove();
            }
        }
        return list;
    }

    @Override
	public Object[] getElements(Object element) {
        return getChildren(element);
    }
