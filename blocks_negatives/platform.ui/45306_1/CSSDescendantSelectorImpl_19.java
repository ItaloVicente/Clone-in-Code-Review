        ExtendedSelector p = (ExtendedSelector)getAncestorSelector();
        if (!((ExtendedSelector)getSimpleSelector()).match(e,pseudoE))
            return false;
        for (Node n = e.getParentNode(); n != null; n = n.getParentNode()) {
            if (n.getNodeType() == Node.ELEMENT_NODE &&
                p.match((Element)n, null)) {
                return true;
            }
        }
        return false;
    }
