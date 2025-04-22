	@Test
	public void testParamizedCommandsSimple() {
		ECommandService cs = workbenchContext.get(ECommandService.class);
		IParameter[] parms = new IParameter[1];
		 parms[0] = new IParameter() {
			@Override
			public String getId() {
				return "viewId";
			}

			@Override
			public String getName() {
				return "View Id";
			}

			@Override
			public IParameterValues getValues() {
				return null;
			}

			@Override
			public boolean isOptional() {
				return false;
			}
		};
		Category defineCategory = cs.defineCategory(TEST_CAT1, "CAT1", null);
		Command command = cs.defineCommand(TEST_ID1_WITH_PARAMETERS, "TEST_ID1_WITH_PARAMETERS", null, defineCategory, parms);
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("viewId", "Testing");
		ParameterizedCommand createdParamizedCommand = cs.createCommand(TEST_ID1_WITH_PARAMETERS, parameters);
		assertNotNull(command);
		assertNotNull(createdParamizedCommand);
		Command cmd1 = cs.getCommand(TEST_ID1_WITH_PARAMETERS);
		assertNotNull("get command1", cmd1);
	}




