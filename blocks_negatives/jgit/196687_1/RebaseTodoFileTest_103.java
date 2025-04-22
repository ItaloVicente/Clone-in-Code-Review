			case 0:
				assertEquals("Expected REWORD", RebaseTodoLine.Action.REWORD,
						line.getAction());
				assertEquals("Unexpected ID", ObjectId.zeroId().abbreviate(40),
						line.getCommit());
				assertEquals("Unexpected Message", "Foo",
						line.getShortMessage());
				break;
			case 1:
				assertEquals("Expected COMMENT", RebaseTodoLine.Action.COMMENT,
						line.getAction());
				assertEquals("Unexpected Message",
						"# A comment in the todo list",
						line.getComment());
				break;
			case 2:
				assertEquals("Expected PICK", RebaseTodoLine.Action.PICK,
						line.getAction());
				assertEquals("Unexpected ID", ObjectId.zeroId().abbreviate(40),
						line.getCommit());
				assertEquals("Unexpected Message", "Foo fie",
						line.getShortMessage());
				break;
			case 3:
				assertEquals("Expected SQUASH", RebaseTodoLine.Action.SQUASH,
						line.getAction());
				assertEquals("Unexpected ID", ObjectId.zeroId().abbreviate(40),
						line.getCommit());
				assertEquals("Unexpected Message", "F", line.getShortMessage());
				break;
			case 4:
				assertEquals("Expected FIXUP", RebaseTodoLine.Action.FIXUP,
						line.getAction());
				assertEquals("Unexpected ID", ObjectId.zeroId().abbreviate(40),
						line.getCommit());
				assertEquals("Unexpected Message", "", line.getShortMessage());
				break;
			case 5:
				assertEquals("Expected EDIT", RebaseTodoLine.Action.EDIT,
						line.getAction());
				assertEquals("Unexpected ID", ObjectId.zeroId().abbreviate(40),
						line.getCommit());
				assertEquals("Unexpected Message", "f", line.getShortMessage());
				break;
			case 6:
				assertEquals("Expected EDIT", RebaseTodoLine.Action.EDIT,
						line.getAction());
				assertEquals("Unexpected ID", ObjectId.zeroId().abbreviate(40),
						line.getCommit());
				assertEquals("Unexpected Message", "", line.getShortMessage());
				break;
			default:
				fail("Too many lines");
				return;
