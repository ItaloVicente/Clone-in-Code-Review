		if (string == null) {

			final StringWriter sw = new StringWriter();
			final BufferedWriter stringBuffer = new BufferedWriter(sw);
			try {
				stringBuffer.write(getTriggerSequence().toString());
				stringBuffer.write(',');
				stringBuffer.newLine();
				stringBuffer.write('\t');
				stringBuffer.write(',');
				stringBuffer.newLine();
				stringBuffer.write('\t');
				stringBuffer.write(schemeId);
				stringBuffer.write(',');
				stringBuffer.newLine();
				stringBuffer.write('\t');
				stringBuffer.write(contextId);
				stringBuffer.write(',');
				stringBuffer.write(',');
				stringBuffer.write(',');
				stringBuffer.write(')');
				stringBuffer.flush();
			} catch (IOException e) {
			}
			string = sw.toString();
		}

