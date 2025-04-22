        String s = e.getAttribute("lang").toLowerCase();
        if (s.equals(lang) || s.startsWith(langHyphen)) {
            return true;
        }
        return s.equals(lang) || s.startsWith(langHyphen);
    }

    /**
     * Fills the given set with the attribute names found in this selector.
     */
    @Override
	public void fillAttributeSet(Set attrSet) {
        attrSet.add("lang");
    }

    /**
     * Returns a text representation of this object.
     */
    @Override
