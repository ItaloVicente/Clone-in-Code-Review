            String name = null;
            ICategory category = (ICategory) element;
            try {
                name = category.getName();
            } catch (NotDefinedException e) {
                name = category.getId();
            }
            if (decorate && isLocked(category)) {
                name = NLS.bind(ActivityMessages.ActivitiesPreferencePage_lockedMessage, name);
            }
            return name;
        }   
       
        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
         */
        public String getColumnText(Object element, int columnIndex) {
        	return getText(element);
        }

        @Override
