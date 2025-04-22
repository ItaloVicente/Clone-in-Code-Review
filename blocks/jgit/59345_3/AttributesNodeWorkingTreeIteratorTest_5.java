		AttributeSet expectedAttrs = new AttributeSet();
		if (nodeAttrs != null) {
			for (Attribute a : nodeAttrs) {
				if (!expectedAttrs.containsKey(a.getKey()))
					expectedAttrs.putAttribute(a);
			}
		}
		if (globalAttrs != null) {
			for (Attribute a : globalAttrs) {
				if (!expectedAttrs.containsKey(a.getKey()))
					expectedAttrs.putAttribute(a);
			}
		}
		if (infoAttrs != null) {
			for (Attribute a : infoAttrs) {
				expectedAttrs.putAttribute(a);
			}
		}

		AttributeSet actualAttrs = db.getAttributesHierarchy()
				.getAttributes(pathName
		assertAttributes(expectedAttrs
