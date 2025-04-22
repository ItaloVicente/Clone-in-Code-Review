				conditional = (ConditionalSelector) item;
			} else if (item instanceof DescendantSelector) {
				if (((DescendantSelector) item).getSimpleSelector() instanceof ConditionalSelector) {
					conditional = (ConditionalSelector) ((DescendantSelector) item).getSimpleSelector();
				} else if (((DescendantSelector) item).getAncestorSelector() instanceof ConditionalSelector) {
					conditional = (ConditionalSelector) ((DescendantSelector) item).getAncestorSelector();
				}
			}
			if (conditional != null) {
				Condition condition = conditional.getCondition();
