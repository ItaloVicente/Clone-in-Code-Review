			String defaultCharset = SystemReader.getInstance()
					.getDefaultCharset().name();
			try {
				if (!encoding.equals(defaultCharset)) {
					sb.append(outputStream.toString(defaultCharset));
					return;
				}
			} catch (UnsupportedEncodingException ignored) {
			}
			try {
				sb.append(outputStream.toString(UTF_8.name()));
			} catch (UnsupportedEncodingException cannotOccur) {
			}
