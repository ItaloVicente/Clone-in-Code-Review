
		assertPersonIdent(" <me@example.com> 1234567890 -0700"
				new PersonIdent(""

		assertPersonIdent(" <> 1234567890 -0700"
				tz));

		assertPersonIdent("<>"

		assertPersonIdent(" <>"

		assertPersonIdent("<me@example.com>"
				"me@example.com"

		assertPersonIdent(" <me@example.com>"
				"me@example.com"

		assertPersonIdent("Me <>"

		assertPersonIdent("Me <me@example.com>"
				"me@example.com"

		assertPersonIdent("Me <me@example.com> 1234567890"
				"Me"

		assertPersonIdent("Me <me@example.com> 1234567890 "
				"Me"
