		} catch (Exception e) {
			assertEquals("Expected to be told about the modified key", 1,
					provider.getLog().size());
			assertTrue("Only messages expected", provider.getLog().stream()
					.flatMap(l -> l.getItems().stream()).allMatch(
							c -> c instanceof CredentialItem.InformationalMessage));
			throw e;
		}
