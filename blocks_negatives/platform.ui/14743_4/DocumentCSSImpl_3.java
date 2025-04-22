							switch (selectorType) {
							case Selector.SAC_CONDITIONAL_SELECTOR:
								ConditionalSelector conditionalSelector = (ConditionalSelector) selector;
								short conditionType = conditionalSelector
										.getCondition().getConditionType();
								if (selectorConditionType == conditionType) {
									list.add(selector);
								}
