				writer.write(chars, 0, read);
			}
		} catch (IOException e) {
			writer.println("Error reading preferences " + e.toString());//$NON-NLS-1$
		}

	}
