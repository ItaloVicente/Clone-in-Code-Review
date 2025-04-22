		command.executeWithChecks(new ExecutionEvent(command, Collections.EMPTY_MAP, null, null));
		command.executeWithChecks(new ExecutionEvent(command, Collections.EMPTY_MAP, null, null));
		command.executeWithChecks(new ExecutionEvent(command, Collections.EMPTY_MAP, null, null));
		command.executeWithChecks(new ExecutionEvent(command, Collections.EMPTY_MAP, null, null));
		command.executeWithChecks(new ExecutionEvent(command, Collections.EMPTY_MAP, null, null));
		command.executeWithChecks(new ExecutionEvent(command, Collections.EMPTY_MAP, null, null));
		Assert.assertEquals(22, text.getFont().getFontData()[0].getHeight());
