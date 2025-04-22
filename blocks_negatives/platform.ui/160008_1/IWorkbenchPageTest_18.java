			assertTrue("At least one should call doSave", callTrace
					.contains("doSave")
					|| call2.contains("doSave"));
			assertFalse("Both should not call doSave", callTrace
					.contains("doSave")
					&& call2.contains("doSave"));
