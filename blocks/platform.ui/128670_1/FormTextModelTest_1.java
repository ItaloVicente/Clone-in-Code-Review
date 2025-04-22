	private void goParse(String lin, String lout) {
		FormTextModel formTextModel = new FormTextModel();
		formTextModel.parseTaggedText("<form>" + lin + "</form>", false);
		assertEquals(lout + System.lineSeparator(), formTextModel.getAccessibleText());
	}

	@Test
	public void testAmpersandEscapes1() {
		String lin = "the &apos;quick&apos; & brown fox";
		String lout = "the 'quick' & brown fox";
		goParse(lin, lout);
	}

	@Test
	public void testAmpersandEscapes2() {
		String lin = "the &amp;quick&amp; & brown fox";
		String lout = "the &quick& & brown fox";
		goParse(lin, lout);
	}

	@Test
	public void testAmpersandEscapes3() {
		String lin = "the &lt;quick&gt; & brown fox";
		String lout = "the <quick> & brown fox";
		goParse(lin, lout);
	}

	@Test
	public void testAmpersandEscapes4() {
		String lin = "&&quot;&lt;&&apos;&&apos;&gt;&&amp;&";
		String lout = "&\"<&'&'>&&&";
		goParse(lin, lout);
	}

	@Test
	public void testAmpersandEscapes5() {
		String lin = "the &apos;quick&quot; & brown fox";
		String lout = "the 'quick\" & brown fox";
		goParse(lin, lout);
	}
