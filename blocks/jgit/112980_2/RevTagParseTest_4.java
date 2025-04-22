				.getBytes(UTF_8));
		b.write("type tree\n".getBytes(UTF_8));
		b.write("tag v1.2.3.4.5\n".getBytes(UTF_8));
		b.write("tagger F\u00f6r fattare <a_u_thor@example.com> 1218123387 +0700\n"
				.getBytes(ISO_8859_1));
		b.write("\n".getBytes(UTF_8));
		b.write("Sm\u00f6rg\u00e5sbord\n".getBytes(UTF_8));
		b.write("\n".getBytes(UTF_8));
		b.write("\u304d\u308c\u3044\n".getBytes(UTF_8));
