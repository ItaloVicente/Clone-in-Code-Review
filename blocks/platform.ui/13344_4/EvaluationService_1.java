				if (!evaluated) {
					names = info.getAccessedVariableNames();
					for (String name : names) {
						if (propertyName.equals(name)) {
							evaluated = true;
							ref.evaluate();
							break;
						}
					}
				}
