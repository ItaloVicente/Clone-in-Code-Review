		public void testMementoWithTextContent113659() throws Exception {
			IMemento memento = XMLMemento.createWriteRoot("root");
			IMemento mementoWithChild = XMLMemento.createWriteRoot("root");
			IMemento child = mementoWithChild.createChild("child");
			child.putTextData("text");
			memento.putMemento(mementoWithChild);
			IMemento copiedChild = memento.getChild("child");
			assertEquals("text", copiedChild.getTextData());
		}
