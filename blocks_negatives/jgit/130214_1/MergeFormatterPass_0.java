	 * @deprecated use
	 *             {@link #MergeFormatterPass(OutputStream, MergeResult, List, Charset)}
	 *             instead.
	 * @param out
	 *            the {@link java.io.OutputStream} where to write the textual
	 *            presentation
	 * @param res
	 *            the merge result which should be presented
	 * @param seqName
	 *            When a conflict is reported each conflicting range will get a
	 *            name. This name is following the "&lt;&lt;&lt;&lt;&lt;&lt;&lt;
	 *            " or "&gt;&gt;&gt;&gt;&gt;&gt;&gt; " conflict markers. The
	 *            names for the sequences are given in this list
	 * @param charsetName
	 *            the name of the character set used when writing conflict
	 *            metadata
	 */
	@Deprecated
	MergeFormatterPass(OutputStream out, MergeResult<RawText> res, List<String> seqName,
			String charsetName) {
		this(out, res, seqName, Charset.forName(charsetName));
	}

	/**
	 *
