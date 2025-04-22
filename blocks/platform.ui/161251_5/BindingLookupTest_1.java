	@Test
	public void testFindBindingInParent_Bug562263() {

		EContextService es = workbenchContext.get(EContextService.class);
		es.deactivateContext(ID_DIALOG_AND_WINDOW);
		es.activateContext(ID_WINDOW);

		ECommandService cs = workbenchContext.get(ECommandService.class);
		ParameterizedCommand cmd = cs.createCommand(TEST_ID1, null);
		EBindingService bs = workbenchContext.get(EBindingService.class);
		TriggerSequence seq = bs.createSequence("CTRL+5 T");
		Binding db = createDefaultBinding(bs, seq, cmd, ID_DIALOG_AND_WINDOW);
		bs.activateBinding(db);
		Binding perfectMatch = bs.getPerfectMatch(seq);
		assertEquals(cmd, perfectMatch.getParameterizedCommand());
		bs.deactivateBinding(db);
		assertNull(bs.getPerfectMatch(seq));

		bs.activateBinding(db);
		assertEquals(cmd, bs.getPerfectMatch(seq).getParameterizedCommand());
	}

