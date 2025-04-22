            super.dispose();
            manager.dispose();
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.ui.activities.IActivityManagerListener#activityManagerChanged(org.eclipse.ui.activities.ActivityManagerEvent)
         */
        public void activityManagerChanged(
                ActivityManagerEvent activityManagerEvent) {
            if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
                updateCategoryCheckState();
                fireLabelProviderChanged(new LabelProviderChangedEvent(this));
            }
        }
    }

    private class CategoryContentProvider implements IStructuredContentProvider {

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         */
        public Object[] getElements(Object inputElement) {
            return WorkbenchActivityHelper.resolveCategories(workingCopy,
                    (Set) inputElement);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        public void dispose() {

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         */
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

        }
    }

    private class EmptyCategoryFilter extends ViewerFilter {

        @Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
