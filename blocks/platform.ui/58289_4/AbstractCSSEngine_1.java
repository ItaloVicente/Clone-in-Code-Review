					attr = (AttributeCondition) condition;
				} else if (condition instanceof CombinatorCondition) {
					if (((CombinatorCondition) condition).getSecondCondition() instanceof AttributeCondition) {
						attr = (AttributeCondition) ((CombinatorCondition) condition).getSecondCondition();
					} else if (((CombinatorCondition) condition).getFirstCondition() instanceof AttributeCondition) {
						attr = (AttributeCondition) ((CombinatorCondition) condition).getFirstCondition();
					}
				}
				if (attr != null) {
					String value = attr.getValue();
