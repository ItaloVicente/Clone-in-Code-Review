
			assertThrows(StringIndexOutOfBoundsException.class,
					() -> describe(c4, false, true, -10));
			assertThrows(StringIndexOutOfBoundsException.class,
					() -> describe(c4, false, true, 1));
			assertThrows(StringIndexOutOfBoundsException.class,
					() -> describe(c4, false, true, 41));
