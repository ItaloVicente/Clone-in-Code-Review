        IStructuredSelection ssel = (IStructuredSelection) sel;

        if(canAdapt()) {
           IStructuredSelection newSelection = LegacyResourceSupport.adaptSelection(ssel, getObjectClass());
           if(newSelection.size() != ssel.size()) {
        	   if (Policy.DEBUG_CONTRIBUTIONS) {
