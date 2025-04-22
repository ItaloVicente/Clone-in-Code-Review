					Attribute raw = attributeIte.previous();
					if (manager == null) {
						if (!attributes.containsKey(raw.getKey()))
							attributes.putAttribute(raw);
					} else {
						for (Attribute attr : manager.expandMacro(raw)) {
							if (!attributes.containsKey(attr.getKey()))
								attributes.putAttribute(attr);
						}
					}
