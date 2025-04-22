				values[i] = value.toString();
			} catch (CoreException e) {
				Policy.handle(e);
				return null;
			}
		}

		return new MarkerQueryResult(values);
	}

	@Override
