				outer: for (Object element : this) {
for (int i = 0; i < toRetain.length; i++)
				if (element == toRetain[i])
					continue outer;
remove(element);
changed = true;
}
