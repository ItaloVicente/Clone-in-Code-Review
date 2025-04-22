			if (macroExpander == null) {
				AttributesNode globalNode = attributesNodeProvider
						.getGlobalAttributesNode();
				AttributesNode infoNode = attributesNodeProvider
						.getInfoAttributesNode();
				AttributesNode rootNode = getRootAttributesNode();
				macroExpander = new MacroExpander(globalNode
						rootNode);
			}
