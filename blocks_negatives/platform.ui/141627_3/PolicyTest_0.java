			assertTrue("expecting severity", message.indexOf("CANCEL") != -1);
			assertTrue("expecting plugin id",
					message.indexOf("somePluginId") != -1);
			assertTrue("expecting message",
					message.indexOf("someMessage") != -1);
			assertTrue("expecting RuntimeException", message
					.indexOf("RuntimeException") != -1);
