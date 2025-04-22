        Object[] children = childMap.get(element);
        if (children == null) {
            children = createChildren(element);
            childMap.put(element, children);
        }
        return children;
    }

    /**
	 * Does the actual work of getChildren.
	 */
    private Object[] createChildren(Object element) {
        if (element instanceof IViewRegistry) {
            IViewRegistry reg = (IViewRegistry) element;
            IViewCategory [] categories = reg.getCategories();

			ArrayList<IViewCategory> filtered = new ArrayList<IViewCategory>();
            for (int i = 0; i < categories.length; i++) {
                if (!hasChildren(categories[i])) {
					continue;
				}

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
					}
                    filtered.add(o);
                }
                return removeIntroView(filtered).toArray();
            }
        }

        return new Object[0];
    }

    /**
	 * Removes the temporary intro view from the list so that it cannot be
	 * activated except through the introduction command.
