        Node n = e;
        if (!((ExtendedSelector)getSiblingSelector()).match(e, pseudoE))
            return false;
        while ((n = n.getPreviousSibling()) != null &&
               n.getNodeType() != Node.ELEMENT_NODE);

        if (n == null)
            return false;

        return ((ExtendedSelector)getSelector()).match((Element)n, null);
    }

    /**
     * Fills the given set with the attribute names found in this selector.
     */
    @Override
	public void fillAttributeSet(Set attrSet) {
        ((ExtendedSelector)getSelector()).fillAttributeSet(attrSet);
        ((ExtendedSelector)getSiblingSelector()).fillAttributeSet(attrSet);
    }

    /**
     * Returns a representation of the selector.
     */
    @Override
