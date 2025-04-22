				if (local != null && obj.equals(local.getObjectId()))
					continue;
				if (askFor.containsKey(obj) || transport.local.hasObject(obj))
					wantTag(r);
				else
					additionalTags.add(r);
