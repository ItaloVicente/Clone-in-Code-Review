        switch (change.getKind()) {
        case TestModelChange.INSERT:
            doInsert(change);
            break;
        case TestModelChange.REMOVE:
            doRemove(change);
            break;
        case TestModelChange.STRUCTURE_CHANGE:
            doStructureChange(change);
            break;
        case TestModelChange.NON_STRUCTURE_CHANGE:
            doNonStructureChange(change);
            break;
        default:
            throw new IllegalArgumentException("Unknown kind of change");
        }
