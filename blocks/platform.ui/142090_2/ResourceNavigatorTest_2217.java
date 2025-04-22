		assertTrue("First selection wrong size, should be only one.", treeSel1
				.size() == 1);
		IResource resource1 = (IResource) treeSel1.getFirstElement();
		assertTrue("First selection contains wrong file resource.", resource1
				.equals(f1));

		IStructuredSelection sel2 = new StructuredSelection(p2);
		part.selectReveal(sel2);
