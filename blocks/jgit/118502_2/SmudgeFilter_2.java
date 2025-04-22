			if (totalRead == 0 && length == -1) {
				in.close();
				out.close();
				return length;
			}

			return totalRead;
		} catch (IOException e) {
