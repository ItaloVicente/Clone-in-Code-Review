		AttributesNode attributeNode = itr.getEntryAttributesNode();
		assertAttributeNode(pathName, attributeNode, nodeAttrs);
		AttributesNode infoAttributeNode = itr.getInfoAttributesNode();
		assertAttributeNode(pathName, infoAttributeNode, infoAttrs);
		AttributesNode globalAttributeNode = itr.getGlobalAttributesNode();
		assertAttributeNode(pathName, globalAttributeNode, globalAttrs);
