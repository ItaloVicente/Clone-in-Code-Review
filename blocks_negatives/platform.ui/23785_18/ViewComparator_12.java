    /**
     * Returns a negative, zero, or positive number depending on whether
     * the first element is less than, equal to, or greater than
     * the second element.
     */
    @Override
	public int compare(Viewer viewer, Object e1, Object e2) {
        if (e1 instanceof IViewDescriptor) {
            String str1 = DialogUtil.removeAccel(((IViewDescriptor) e1)
                    .getLabel());
            String str2 = DialogUtil.removeAccel(((IViewDescriptor) e2)
                    .getLabel());
            return getComparator().compare(str1, str2);
        } else if (e1 instanceof IViewCategory) {
			IViewCategory generalCategory = viewReg.findCategory(GENERAL_VIEW_ID);
        	if (generalCategory != null){
        		if (((IViewCategory)e1).getId().equals(generalCategory.getId())) {
					return -1;
				}
        		if (((IViewCategory)e2).getId().equals(generalCategory.getId())) {
					return 1;
				}
        	}
			Category miscCategory = viewReg.getMiscCategory();
			if(miscCategory != null){
				if (((IViewCategory)e1).getId().equals(miscCategory.getId())) {
					return 1;
				}
				if (((IViewCategory)e2).getId().equals(miscCategory.getId())) {
					return -1;
				}
			}
            String str1 = DialogUtil.removeAccel(((IViewCategory) e1).getLabel());
            String str2 = DialogUtil.removeAccel(((IViewCategory) e2).getLabel());
            return getComparator().compare(str1, str2);
        }
        return 0;
    }
