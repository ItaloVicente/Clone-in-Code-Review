
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
				"me@example.com"

		assertPersonIdent("Me <me@example.com> 1234567890 "
				"me@example.com"
