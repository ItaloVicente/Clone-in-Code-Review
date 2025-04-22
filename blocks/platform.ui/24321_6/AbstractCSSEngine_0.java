		if ("inherit".equals(value.getCssText())) {
			Element actualElement = (Element) element;
			Node parentNode = actualElement.getParentNode();
			String parentValueString = retrieveCSSProperty(parentNode,
					property, pseudo);
			value = parsePropertyValue(parentValueString);
		}

