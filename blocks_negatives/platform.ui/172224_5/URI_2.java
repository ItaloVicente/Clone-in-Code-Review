			}
			else
			{
				if (SEGMENT_PARENT.equals(stack[sp - 1])) {
				stack[sp++] = segment;
			} else {
				sp--;
			}
			}
