				w.flush();
				os.write('\n');
			}

			if (!References.isSameObject(getEncoding()
				os.write(hencoding);
				os.write(' ');
				os.write(Constants.encodeASCII(getEncoding().name()));
				os.write('\n');
			}

			os.write('\n');
			String msg = getMessage();
			if (msg != null) {
				w.write(msg);
				w.flush();
