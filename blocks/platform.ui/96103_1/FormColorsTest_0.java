			assertEquals("Different concurrent instances of FormColors did not return the same Color for key: "
					+ KEYS_NULL[i], nullColors[i], nullColors2[i]);
		assertEquals(
				"Different concurrent instances of FormColors did not return the same Color for getInactiveBackground()",
				inactiveBg, inactiveBg2);
		assertEquals("Different concurrent instances of FormColors did not return the same Color for getBackground()",
				bg, bg2);
		assertEquals("Different concurrent instances of FormColors did not return the same Color for getForeground()",
				fg, fg2);
		assertEquals("Different concurrent instances of FormColors did not return the same Color for getBorderColor()",
				bc, bc2);
