				return null;
			}
		};

		try {
			cv.setValue(new Object());
			fail("exception should have been thrown");
		} catch (UnsupportedOperationException e) {
		}
	}
