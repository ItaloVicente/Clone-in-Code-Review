    	}

        return staticActionBuilders.keySet();
    }

    /**
     * <p>
     * Adds another menu identifier to this extender. An extender can represent
     * many menu identifiers. These identifiers should represent the same menu
     * manager, selection provider and part. Duplicate identifiers are
     * automatically ignored.
     * </p>
     * <p>
     * For example, it is necessary to filter out duplicate identifiers for
     * <code>CompilationUnitEditor</code> instances, as these define both
     * <code>"#CompilationUnitEditorContext"</code> and
     * <code>"org.eclipse.jdt.ui.CompilationUnitEditor.EditorContext"</code>
     * as menu identifier for the same pop-up menu. We don't want to contribute
     * duplicate items in this case.
     * </p>
     *
     * @param menuId
     *            The menu identifier to add to this extender; should not be
     *            <code>null</code>.
     */
    public final void addMenuId(final String menuId) {
