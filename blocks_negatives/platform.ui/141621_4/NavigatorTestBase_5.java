		for (int i = 0; i < items.length; i++) {
			assertTrue("Child " + items[i] + " should be an M1 or M2 resource",
					items[i].getData() instanceof ResourceWrapper);
			ResourceWrapper rw = (ResourceWrapper) items[i].getData();
			if (name.equals(rw.getResource().getName())) {
				return items[i];
			}
