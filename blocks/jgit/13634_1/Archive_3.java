		} catch (FileNotFoundException e) {
			throw die(e.getMessage());
		} finally {
			if (output != null && stream != null)
				stream.close();
		}
