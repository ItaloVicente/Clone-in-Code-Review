			if (cnt == 0)
				children = new ReverseCommit[] { c };
			else if (cnt == 1)
				children = new ReverseCommit[] { c, children[0] };
			else {
				ReverseCommit[] n = new ReverseCommit[1 + cnt];
				n[0] = c;
				System.arraycopy(children, 0, n, 1, cnt);
				children = n;
			}
