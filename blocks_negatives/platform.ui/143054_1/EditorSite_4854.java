        }
    }

    @Override
	public final void registerContextMenu(final MenuManager menuManager,
            final ISelectionProvider selectionProvider,
            final boolean includeEditorInput) {
        registerContextMenu(getId(), menuManager, selectionProvider,
                includeEditorInput);
    }

    @Override
	public final void registerContextMenu(final String menuId,
            final MenuManager menuManager,
            final ISelectionProvider selectionProvider,
            final boolean includeEditorInput) {
        if (menuExtenders == null) {
            menuExtenders = new ArrayList(1);
        }

		PartSite.registerContextMenu(menuId, menuManager, selectionProvider, includeEditorInput,
				getPart(), getModel().getContext(), menuExtenders);
    }
