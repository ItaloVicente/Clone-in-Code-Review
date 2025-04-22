                values[i] = value.toString();
            } catch (CoreException e) {
            	Policy.handle(e);
            	return null;
            }
        }

        return new MarkerQueryResult(values);
    }

    @Override
	public boolean equals(Object o) {
        if (!(o instanceof MarkerQuery)) {
			return false;
