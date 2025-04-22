			Control button = item.getControl();
			fontData = button.getFont().getFontData()[0];
			assertEquals("Verdana", fontData.getName());
			assertEquals(12, fontData.getHeight());
			assertEquals(SWT.NORMAL, fontData.getStyle());
		}
