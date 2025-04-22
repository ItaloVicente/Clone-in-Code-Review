						.getBytes(ISO_8859_1));
		b.write("encoding EUC-JP\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("\u304d\u308c\u3044\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("Hi\n".getBytes(UTF_8));
