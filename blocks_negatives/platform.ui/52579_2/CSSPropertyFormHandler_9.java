					List values = grad.getValues();
					List colors = new ArrayList(values.size());
					for (Iterator it = values.iterator(); it.hasNext();) {
						Object object = (Object) it.next();
						if (object instanceof CSSValue) {
							CSSValue cssValue = (CSSValue) object;
