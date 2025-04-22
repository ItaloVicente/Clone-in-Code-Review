				case 0:
					assertEquals(RebaseTodoLine.Action.REWORD
							line.getAction()
							"Expected REWORD");
					assertEquals(ObjectId.zeroId().abbreviate(40)
							line.getCommit()
							"Unexpected ID");
					assertEquals("Foo"
							line.getShortMessage()
							"Unexpected Message");
					break;
				case 1:
					assertEquals(RebaseTodoLine.Action.COMMENT
							line.getAction()
							"Expected COMMENT");
					assertEquals("# A comment in the todo list"
							line.getComment()
							"Unexpected Message");
					break;
				case 2:
					assertEquals(RebaseTodoLine.Action.PICK
							line.getAction()
							"Expected PICK");
					assertEquals(ObjectId.zeroId().abbreviate(40)
							line.getCommit()
							"Unexpected ID");
					assertEquals("Foo fie"
							line.getShortMessage()
							"Unexpected Message");
					break;
				case 3:
					assertEquals(RebaseTodoLine.Action.SQUASH
							line.getAction()
							"Expected SQUASH");
					assertEquals(ObjectId.zeroId().abbreviate(40)
							line.getCommit()
							"Unexpected ID");
					assertEquals("F"
					break;
				case 4:
					assertEquals(RebaseTodoLine.Action.FIXUP
							line.getAction()
							"Expected FIXUP");
					assertEquals(ObjectId.zeroId().abbreviate(40)
							line.getCommit()
							"Unexpected ID");
					assertEquals(""
					break;
				case 5:
					assertEquals(RebaseTodoLine.Action.EDIT
							line.getAction()
							"Expected EDIT");
					assertEquals(ObjectId.zeroId().abbreviate(40)
							line.getCommit()
							"Unexpected ID");
					assertEquals("f"
					break;
				case 6:
					assertEquals(RebaseTodoLine.Action.EDIT
							line.getAction()
							"Expected EDIT");
					assertEquals(ObjectId.zeroId().abbreviate(40)
							line.getCommit()
							"Unexpected ID");
					assertEquals(""
					break;
				default:
					fail("Too many lines");
					return;
