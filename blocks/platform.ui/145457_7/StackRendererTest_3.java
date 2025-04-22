		MPartDescriptor partDescriptor = ems.createModelElement(MPartDescriptor.class);
		partDescriptor.setElementId("myelementid");
		partDescriptor.setLabel("some title");
		partDescriptor.setIconURI(PART_DESC_ICON);
		application.getDescriptors().add(partDescriptor);

		part1 = ems.createPart(partDescriptor);
		part2 = ems.createPart(partDescriptor);

