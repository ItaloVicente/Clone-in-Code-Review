    @InterfaceAudience.Private
    public SubdocOptionsBuilder expandMacros(boolean expandMacros) {
        this.expandMacros = expandMacros;
        return this;
    }

    @InterfaceAudience.Private
    public boolean expandMacros() {
        return this.expandMacros;
    }
