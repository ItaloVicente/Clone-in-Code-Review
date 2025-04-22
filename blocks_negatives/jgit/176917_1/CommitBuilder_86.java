			if (!References.isSameObject(getEncoding(), UTF_8)) {
				os.write(hencoding);
				os.write(' ');
				os.write(Constants.encodeASCII(getEncoding().name()));
				os.write('\n');
			}
