						.getBytes("ISO-8859-1"));
		b.write("encoding EUC-JP\n".getBytes("UTF-8"));
		b.write("\n".getBytes("UTF-8"));
		b.write("\u304d\u308c\u3044\n".getBytes("UTF-8"));
		b.write("\n".getBytes("UTF-8"));
		b.write("Hi\n".getBytes("UTF-8"));
