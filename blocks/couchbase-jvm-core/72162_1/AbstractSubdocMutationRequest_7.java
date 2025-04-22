    @Override
    public boolean attributeAccess() {
        return this.attributeAccess;
    }

    @Override
    public boolean expandMacros() {
        return this.expandMacros;
    }

    public void attributeAccess(boolean attributeAccess) {
        if (!attributeAccess && this.expandMacros) {
            throw new IllegalArgumentException("Macros can be used only with extended attributes");
        }
        this.attributeAccess = attributeAccess;
    }

    public void expandMacros(boolean expandMacros) {
        if (!this.attributeAccess && expandMacros) {
            throw new IllegalArgumentException("Macros can be used only with extended attributes");
        }
        this.expandMacros = expandMacros;
    }

