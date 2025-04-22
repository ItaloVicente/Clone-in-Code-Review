	public class InputTester extends EventTester {
		InputTester(IEventBroker eventBroker) {
			super("Input", Input.TOPIC_ALL, new String[] { Input.INPUTURI },
					eventBroker);
		}
	}

