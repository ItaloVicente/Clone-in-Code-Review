			final StringWriter sw = new StringWriter();
			final BufferedWriter buffer = new BufferedWriter(sw);
			try {
				buffer.write(id);
				buffer.write(',');
				buffer.write(',');
				buffer.newLine();
				buffer.write(',');
				buffer.newLine();
				buffer.write(',');
				buffer.newLine();
				buffer.write(',');
				buffer.newLine();
				buffer.write(',');
				buffer.write(',');
				buffer.write(')');
				buffer.flush();
			} catch (IOException e) {
			}
			string = sw.toString();
