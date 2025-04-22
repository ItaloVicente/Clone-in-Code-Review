    private ITabbedPropertySheetPageContributor getTabbedPropertySheetPageContributor(
            Object object) {
        if (object instanceof ITabbedPropertySheetPageContributor) {
            return (ITabbedPropertySheetPageContributor) object;
        }

        if (object instanceof IAdaptable
            && ((IAdaptable) object)
                .getAdapter(ITabbedPropertySheetPageContributor.class) != null) {
            return (ITabbedPropertySheetPageContributor) (((IAdaptable) object)
                .getAdapter(ITabbedPropertySheetPageContributor.class));
        }

        if (Platform.getAdapterManager().hasAdapter(object,
            ITabbedPropertySheetPageContributor.class.getName())) {
            return (ITabbedPropertySheetPageContributor) Platform
                .getAdapterManager().loadAdapter(object,
                    ITabbedPropertySheetPageContributor.class.getName());
        }

        return null;
