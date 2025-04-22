            	l.set(index, convert(value, pd.getEType().getInstanceClass()));
            }
            catch (Exception ex) {
                throw new RuntimeException(
                    "Cannot access property: "
                        + pd.getName()
                        + ", "
                        + ex.getMessage());
            }
        }
        Object collection = getValue(bean, pd);
        if (isCollection(collection)) {
            setValue(collection, index, value);
        }
        else if (index == 0) {
            setValue(bean, pd, value);
        }
        else {
            throw new RuntimeException(
                "Not a collection: " + pd.getName());
        }
