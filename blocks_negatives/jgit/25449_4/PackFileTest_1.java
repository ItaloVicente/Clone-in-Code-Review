			ol.getCachedBytes();
			fail("Should have thrown LargeObjectException");
		} catch (LargeObjectException tooBig) {
			assertEquals(MessageFormat.format(
					JGitText.get().largeObjectException, id3.name()), tooBig
					.getMessage());
