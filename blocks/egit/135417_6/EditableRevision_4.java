					String charset = editable.getCharset();
					if (charset == null) {
						editable.setContent(document.get().getBytes());
					} else {
						editable.setContent(document.get().getBytes(charset));
					}
