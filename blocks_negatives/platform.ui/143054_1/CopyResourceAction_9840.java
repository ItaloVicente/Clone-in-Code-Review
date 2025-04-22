        if (!super.updateSelection(selection)) {
            return false;
        }
        if (getSelectedNonResources().size() > 0) {
            return false;
        }
