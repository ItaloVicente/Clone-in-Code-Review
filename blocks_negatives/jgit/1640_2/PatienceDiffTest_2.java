	public void testEdit_UniqueCommonLargerThanMatchPoint() {
		EditList r = diff(t("AbdeZ"), t("PbdeQR"));
		assertEquals(2, r.size());
		assertEquals(new Edit(0, 1, 0, 1), r.get(0));
		assertEquals(new Edit(4, 5, 4, 6), r.get(1));
	}

	public void testEdit_CommonGrowsPrefixAndSuffix() {
		EditList r = diff(t("AaabccZ"), t("PaabccR"));
		assertEquals(2, r.size());
		assertEquals(new Edit(0, 1, 0, 1), r.get(0));
		assertEquals(new Edit(6, 7, 6, 7), r.get(1));
	}

	public void testEdit_DuplicateAButCommonUniqueInB() {
		EditList r = diff(t("AbbcR"), t("CbcS"));
		assertEquals(2, r.size());
		assertEquals(new Edit(0, 2, 0, 1), r.get(0));
		assertEquals(new Edit(4, 5, 3, 4), r.get(1));
	}

