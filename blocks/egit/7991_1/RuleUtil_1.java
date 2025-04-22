				IContainer container = resource.getParent();
				if (!(container instanceof IWorkspaceRoot)) {
					ISchedulingRule rule = ruleFactory.modifyRule(container);
					if (rule != null)
						rules.add(rule);
				}
