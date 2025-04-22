			while (id != null) {
				Context context = contextManager.getContext(id);

				if (!contexts.add(context) || !context.isDefined()) {
					break;
				}

				try {
					id = context.getParentId();
				} catch (NotDefinedException e) {
				}
			}
