
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (!writeFailure && writer != null) {
					try {
						writer.write(line);
						writer.newLine();
						writer.flush();
					} catch (IOException e) {
						writeFailure = true;
