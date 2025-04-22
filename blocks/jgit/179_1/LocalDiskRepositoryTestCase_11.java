	protected static void assertEquals(AnyObjectId exp
		if (exp != null)
			exp = exp.copy();
		if (act != null)
			act = act.copy();
		Assert.assertEquals(exp
	}

