					throw new SAXException(e);
				}
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						throw new SAXException(e);
					}
