		for (int i = 0; i < visibleIds.length; i++) {
			if (!TEST_CONTENT.equals(visibleIds[i])
					&& !COMMON_NAVIGATOR_RESOURCE_EXT.equals(visibleIds[i])
					&& !TEST_CONTENT_HAS_CHILDREN.equals(visibleIds[i])) {
				assertTrue("The extension id is invalid:" + visibleIds[i],
						false);
