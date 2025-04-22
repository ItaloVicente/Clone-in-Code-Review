						FileMode.TREE.copyTo(out);
						out.write(' ');
						out.write(st.encodedName);
						out.write(0);
						st.id.copyRawTo(out);

