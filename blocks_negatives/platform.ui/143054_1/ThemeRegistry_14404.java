        return descriptors[idx];
    }

    /*
     * Add newElement to descriptors.
     * If one with the same id already exists, replace it.
     * Return the existing element in the case of replacing, null in the case of adding.
     */
    private IThemeElementDefinition addOrReplaceDescriptor(
            List descriptors, IThemeElementDefinition newElement) {
    	String id = newElement.getId();
    	for (int i = 0; i < descriptors.size(); i++) {
    		IThemeElementDefinition existingElement = (IThemeElementDefinition) descriptors.get(i);
    		if(existingElement.getId().equals(id)) {
    			descriptors.remove(i);
    			descriptors.add(newElement);
    			return existingElement;
    		}
