        }
        return imageDesc;
    }

    /**
     * Creates the menu item for the editor descriptor.
     *
     * @param menu the menu to add the item to
     * @param descriptor the editor descriptor, or null for the system editor
     * @param preferredEditor the descriptor of the preferred editor, or <code>null</code>
     */
    private void createMenuItem(Menu menu, final IEditorDescriptor descriptor,
            final IEditorDescriptor preferredEditor) {
        final MenuItem menuItem = new MenuItem(menu, SWT.RADIO);
