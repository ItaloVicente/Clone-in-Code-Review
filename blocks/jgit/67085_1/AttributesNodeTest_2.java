
		for (AttributesRule rule : node.getRules()) {
			if (rule.isMatch(path
				ListIterator<Attribute> attributeIte = rule.getAttributes()
						.listIterator(rule.getAttributes().size());
				while (attributeIte.hasPrevious()) {
					Attribute a = attributeIte.previous();
					if (!attributes.containsKey(a.getKey())) {
						attributes.put(a);
					}
				}
			}
		}
