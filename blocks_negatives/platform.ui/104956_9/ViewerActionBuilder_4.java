				if (actions != null) {
					for (int i = 0; i < actions.size(); i++) {
						PluginAction proxy = ((ActionDescriptor) actions.get(i))
								.getAction();
						proxy.selectionChanged(event);
					}
