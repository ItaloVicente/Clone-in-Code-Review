					String escaped = escapeValue(e.subsection);
					boolean quoted = escaped.startsWith("\"")
							&& escaped.endsWith("\"");
					if (!quoted)
						out.append('"');
					out.append(escaped);
					if (!quoted)
						out.append('"');
