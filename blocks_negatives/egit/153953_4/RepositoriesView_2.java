			if (needsNewInput) {
				Object[] expanded = tv.getExpandedElements();
				tv.setInput(ResourcesPlugin.getWorkspace().getRoot());
				tv.setExpandedElements(expanded);
				afterRefresh(tv);
			} else {
				tv.refresh(true);
			}
