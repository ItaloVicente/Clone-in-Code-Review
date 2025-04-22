		 * The QUICK_ACCESS_ELEMENT_IDS array contains the IDs of optional
		 * elements provided via an e4xmi application model fragment. The method
		 * checks if they should be moved to a special position. This behavior
		 * is required because nearly all elements in the legacy workbench are
		 * not provided via e4xmi application model. They are provided
		 * programmatically after the e4xmi application model and the
		 * corresponding fragment models are already processed.
