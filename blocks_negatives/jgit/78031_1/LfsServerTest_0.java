				BufferedInputStream bis = new BufferedInputStream(
						response.getEntity().getContent());
				ByteArrayOutputStream buf = new ByteArrayOutputStream();
				int result = bis.read();
				while (result != -1) {
					buf.write((byte) result);
					result = bis.read();
