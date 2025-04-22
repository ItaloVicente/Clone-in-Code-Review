							projectReferences.add(projectReference);
						} catch (final IllegalArgumentException e) {
							throw new TeamException(reference, e);
						} catch (final URISyntaxException e) {
							throw new TeamException(reference, e);
						}
