			pin(pack, position);

			int ptr = (int) (position - window.start);
			int n = (int) Math.min(window.size() - ptr, remaining);
			window.write(out, position, n);
			position += n;
			remaining -= n;
