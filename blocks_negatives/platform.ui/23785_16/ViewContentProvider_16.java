                filtered.add(categories[i]);
            }
			categories = filtered.toArray(new IViewCategory[filtered
                    .size()]);

            if (categories.length == 1) {
                return getChildren(categories[0]);
            }
            return categories;
        } else if (element instanceof IViewCategory) {
            IViewDescriptor [] views = ((IViewCategory) element).getViews();
            if (views != null) {
                ArrayList<Object> filtered = new ArrayList<Object>();
                for (int i = 0; i < views.length; i++) {
                    Object o = views[i];
                    if (WorkbenchActivityHelper.filterItem(o)) {
						continue;
