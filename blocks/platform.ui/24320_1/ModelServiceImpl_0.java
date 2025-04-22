
			for (MBindingContext bindingContext : app.getBindingContexts()) {
				findElementsRecursive(bindingContext, matcher, elements, searchFlags);
			}

			for (MBindingTable bindingTable : app.getBindingTables()) {
				findElementsRecursive(bindingTable, matcher, elements, searchFlags);
			}
