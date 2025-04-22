			Inflater decompressor = new Inflater();
			decompressor.setInput(bytes, 0, bytes.length);
			byte[] result = new byte[expectedSize];
			decompressor.inflate(result);
			decompressor.end();
			return result;
