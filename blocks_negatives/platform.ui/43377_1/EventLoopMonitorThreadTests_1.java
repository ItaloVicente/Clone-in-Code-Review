		StringBuilder buf = new StringBuilder(" Timeline: ");
		for (StackSample sample : event.getStackTraceSamples()) {
			buf.append(sample.getTimestamp());
			buf.append(' ');
		}
		assertEquals("A long running event didn't capture a good range of stack traces" + buf.toString(),
