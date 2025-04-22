        if (!Util.equals(enabled, castedObject.enabled)) {
            return false;
        }

        return Util.equals(id, castedObject.id);
    }

    void fireIdentifierChanged(IdentifierEvent identifierEvent) {
        if (identifierEvent == null) {
