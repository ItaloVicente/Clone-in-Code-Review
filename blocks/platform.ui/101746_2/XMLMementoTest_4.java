		try (StringWriter writer = new StringWriter()) {
			mementoToSerialize.save(writer);
			StringReader reader = new StringReader(writer.getBuffer().toString());
			XMLMemento deserializedMemento = XMLMemento.createReadRoot(reader);
			mementoChecker.checkAfterDeserialization(deserializedMemento);
		}
