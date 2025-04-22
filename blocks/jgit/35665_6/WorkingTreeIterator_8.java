	private InputStream filterClean(InputStream in
		InputStream auxStream = in;
		if (hasIdentSet(attributes))
			auxStream = new org.eclipse.jgit.util.io.IdentInputStream(in

		if (isAutoCrlf())
			auxStream = new EolCanonicalizingInputStream(auxStream

		return auxStream;
