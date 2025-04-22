			assertTrue(
					String.format(
							"result of command (%d) should be %s: %s %s%s",
							Integer.valueOf(i), r, c,
							c.getResult(),
							c.getMessage() != null ? " (" + c.getMessage() + ")" : ""),
