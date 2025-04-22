        if (super.setChecked(element, state)) {
            doCheckStateChanged(element);
            return true;
        }
        return false;
    }

