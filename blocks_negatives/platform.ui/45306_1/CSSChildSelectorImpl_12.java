        SimpleSelector s = getSimpleSelector();
        if (s.getSelectorType() == SAC_PSEUDO_ELEMENT_SELECTOR) {
            return String.valueOf( getAncestorSelector() ) + s;
        }
        return getAncestorSelector() + " > " + s;
    }
