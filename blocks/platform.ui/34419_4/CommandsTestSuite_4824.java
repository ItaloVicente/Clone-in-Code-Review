package org.eclipse.ui.tests.commands;

import java.util.Map;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.SerializationException;
import org.eclipse.core.commands.common.CommandException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class CommandSerializationTest extends UITestCase {

	public CommandSerializationTest(String testName) {
		super(testName);
	}

	private final String showPerspectiveCommandId = "org.eclipse.ui.perspectives.showPerspective";

	public void testSerializeShowPerspective() throws CommandException {

		testDeserializeAndSerialize(showPerspectiveCommandId,
				showPerspectiveCommandId, 0, null, null);

		testDeserializeAndSerialize(showPerspectiveCommandId+"()",
				showPerspectiveCommandId, 0, null, null);
	}


	public void testSerializeShowResourcePerspective() throws CommandException {

		final String serializedShowResourcePerspectiveCommand = "org.eclipse.ui.perspectives.showPerspective(org.eclipse.ui.perspectives.showPerspective.perspectiveId=org.eclipse.ui.resourcePerspective)";
		final String showPerspectiveParameterId = "org.eclipse.ui.perspectives.showPerspective.perspectiveId";
		final String resourcePerspectiveId = "org.eclipse.ui.resourcePerspective";

		testDeserializeAndSerialize(serializedShowResourcePerspectiveCommand,
				showPerspectiveCommandId, 1,
				new String[] { showPerspectiveParameterId },
				new String[] { resourcePerspectiveId });

	}

	public void testZeroParameterCommand() throws CommandException {
		final String zeroParameterCommandId = "org.eclipse.ui.tests.commands.zeroParameterCommand";

		testDeserializeAndSerialize(zeroParameterCommandId,
				zeroParameterCommandId, 0, null, null);

		testDeserializeAndSerialize(zeroParameterCommandId
				+ "(bogus.param=hello)", zeroParameterCommandId, 1, null, null);
	}

	public void testOneParameterCommand() throws CommandException {
		final String oneParameterCommandId = "org.eclipse.ui.tests.commands.oneParameterCommand";
		final String paramId1 = "param1.1";

		testDeserializeAndSerialize(oneParameterCommandId + "(param1.1=hello)",
				oneParameterCommandId, 1, new String[] { paramId1 },
				new String[] { "hello" });

		testDeserializeAndSerialize(oneParameterCommandId + "(param1.1)",
				oneParameterCommandId, 1, new String[] { paramId1 },
				new String[] { null });

		testDeserializeAndSerialize(oneParameterCommandId,
				oneParameterCommandId, 0, null, null);

		testDeserializeAndSerialize(oneParameterCommandId
				+ "(bogus.param=hello)", oneParameterCommandId, 1, null, null);

		testDeserializeAndSerialize(oneParameterCommandId
				+ "(bogus.param=hello,param1.1=foo)", oneParameterCommandId, 2,
				new String[] { paramId1 }, new String[] { "foo" });
	}

	public void testTwoParameterCommand() throws CommandException {
		final String twoParameterCommandId = "org.eclipse.ui.tests.commands.twoParameterCommand";
		final String paramId1 = "param2.1";
		final String paramId2 = "param2.2";

		testDeserializeAndSerialize(twoParameterCommandId
				+ "(param2.1=hello,param2.2=goodbye)", twoParameterCommandId, 2,
				new String[] { paramId1, paramId2 }, new String[] { "hello",
						"goodbye" });

		testDeserializeAndSerialize(twoParameterCommandId
				+ "(param2.2=goodbye,param2.1=hello)", twoParameterCommandId, 2,
				new String[] { paramId1, paramId2 }, new String[] { "hello",
						"goodbye" });

		final String value1Escaped = "hello%(%)%%%=%,";
		final String value2Escaped = "%%%=%(%)%,world";
		testDeserializeAndSerialize(twoParameterCommandId + "(param2.1="
				+ value1Escaped + ",param2.2=" + value2Escaped + ")",
				twoParameterCommandId, 2, new String[] { paramId1, paramId2 },
				new String[] { "hello()%=,", "%=(),world" });
	}

	public void testThreeParameterCommand() throws CommandException {
		final String threeParameterCommandId = "org.eclipse.ui.tests.commands.threeParameterCommand";
		final String paramId1 = "param3.1";
		final String paramId2 = "param3.2";
		final String paramId3 = "param3.3";

		testDeserializeAndSerialize(threeParameterCommandId
				+ "(param3.1=foo,param3.2=bar,param3.3=baz)",
				threeParameterCommandId, 3, new String[] { paramId1, paramId2,
						paramId3 }, new String[] { "foo", "bar", "baz" });

		testDeserializeAndSerialize(threeParameterCommandId
				+ "(param3.1,param3.2=bar,param3.3=baz)",
				threeParameterCommandId, 3, new String[] { paramId1, paramId2,
						paramId3 }, new String[] { null, "bar", "baz" });

		testDeserializeAndSerialize(threeParameterCommandId
				+ "(param3.1,param3.2,param3.3)",
				threeParameterCommandId, 3, new String[] { paramId1, paramId2,
						paramId3 }, new String[] { null, null, null });

		testDeserializeAndSerialize(threeParameterCommandId
				+ "(param3.1=foo,param3.3=baz)", threeParameterCommandId, 2,
				new String[] { paramId1, paramId3 }, new String[] { "foo",
						"baz" });
	}


	public void testFunnyNamesCommand() throws CommandException {
		final String funnyNamesCommandId = "org.eclipse.ui.tests.command.with.f=%)(,unny.name";
		final String funnyNamesCommandIdEncoded = "org.eclipse.ui.tests.command.with.f%=%%%)%(%,unny.name";

		final String funnyNamesParamId = "param.with.F({'><+|.)=,%.name";
		final String funnyNamesParamIdEncoded = "param.with.F%({'><+|.%)%=%,%%.name";

		final String funnyValue = "= %,.&\n\t\r?*[](){}";
		final String funnyValueEncoded = "%= %%%,.&\n\t\r?*[]%(%){}";

		final String serializedFunnyNamesCommand = funnyNamesCommandIdEncoded
				+ "(" + funnyNamesParamIdEncoded + "=" + funnyValueEncoded + ")";

		testDeserializeAndSerialize(serializedFunnyNamesCommand,
				funnyNamesCommandId, 1, new String[] { funnyNamesParamId },
				new String[] { funnyValue });
	}

	public void testMalformedSerializationStrings() {
		expectSerializationException(showPerspectiveCommandId + "(");

		expectSerializationException("some.command.foo%bar");
	}

	public void testUndefinedCommands() {
		expectNotDefinedException("this.command.ain't.defined(i.hope)");
	}

	private void testDeserializeAndSerialize(
			String serializedParameterizedCommand, String commandId,
			int serializedParamCount, String[] paramIds, String[] paramValues)
			throws CommandException {

		ICommandService commandService = getCommandService();

		int realParamCount = (paramIds == null) ? 0 : paramIds.length;

		ParameterizedCommand pCommand = commandService
				.deserialize(serializedParameterizedCommand);
		assertNotNull(pCommand);
		assertEquals(commandId, pCommand.getId());

		Map paramMap = pCommand.getParameterMap();
		assertEquals(realParamCount, paramMap.size());

		if (paramIds != null) {
			for (int i = 0; i < realParamCount; i++) {
				assertTrue(paramMap.containsKey(paramIds[i]));
				assertEquals(paramValues[i], paramMap.get(paramIds[i]));
			}
		}

		String serialization = pCommand.serialize();

		if ((realParamCount == serializedParamCount) && (realParamCount < 2)) {
			if ((realParamCount == 0)
					&& (serializedParameterizedCommand.endsWith("()"))) {
				assertEquals(serializedParameterizedCommand, serialization
						+ "()");
			} else {
				assertEquals(serializedParameterizedCommand, serialization);
			}
		} else {
		}

		ParameterizedCommand pCommand2 = commandService.deserialize(serialization);
		assertEquals(pCommand, pCommand2);
	}

	private void expectSerializationException(String serializedParameterizedCommand) {
		ICommandService commandService = getCommandService();

		try {
			commandService.deserialize(serializedParameterizedCommand);
			fail("expected SerializationException");
		} catch (SerializationException ex) {
		} catch (NotDefinedException ex) {
			fail("expected SerializationException");
		}
	}

	private void expectNotDefinedException(String serializedParameterizedCommand) {
		ICommandService commandService = getCommandService();

		try {
			commandService.deserialize(serializedParameterizedCommand);
			fail("expected NotDefinedException");
		} catch (SerializationException ex) {
			fail("expected NotDefinedException");
		} catch (NotDefinedException ex) {
		}
	}

	private ICommandService getCommandService() {
		Object serviceObject = getWorkbench().getAdapter(ICommandService.class);
		if (serviceObject != null) {
			ICommandService service = (ICommandService) serviceObject;
			return service;
		}
		return null;
	}

}
