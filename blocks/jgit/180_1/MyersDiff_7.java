
package org.eclipse.jgit.diff;

import org.eclipse.jgit.util.IntList;
import org.eclipse.jgit.util.LongList;

public class MyersDiff {
	protected EditList edits;

	protected Sequence a;

	protected Sequence b;

	public MyersDiff(Sequence a
		this.a = a;
		this.b = b;
		calculateEdits();
	}

	public EditList getEdits() {
		return edits;
	}

	MiddleEdit middle = new MiddleEdit();

	protected void calculateEdits() {
		edits = new EditList();

		middle.initialize(0
		if (middle.beginA >= middle.endA &&
				middle.beginB >= middle.endB)
			return;

		calculateEdits(middle.beginA
				middle.beginB
	}

	protected void calculateEdits(int beginA
			int beginB
		Edit edit = middle.calculate(beginA

		if (beginA < edit.beginA || beginB < edit.beginB) {
			int k = edit.beginB - edit.beginA;
			int x = middle.backward.snake(k
			calculateEdits(beginA
		}

		if (edit.getType() != Edit.Type.EMPTY)
			edits.add(edits.size()

		if (endA > edit.endA || endB > edit.endB) {
			int k = edit.endB - edit.endA;
			int x = middle.forward.snake(k
			calculateEdits(x
		}
	}

	class MiddleEdit {
		void initialize(int beginA
			this.beginA = beginA; this.endA = endA;
			this.beginB = beginB; this.endB = endB;

			int k = beginB - beginA;
			this.beginA = forward.snake(k
			this.beginB = k + this.beginA;

			k = endB - endA;
			this.endA = backward.snake(k
			this.endB = k + this.endA;
		}

		Edit calculate(int beginA
			if (beginA == endA || beginB == endB)
				return new Edit(beginA
			this.beginA = beginA; this.endA = endA;
			this.beginB = beginB; this.endB = endB;

			int minK = beginB - endA;
			int maxK = endB - beginA;

			forward.initialize(beginB - beginA
			backward.initialize(endB - endA

			for (int d = 1; ; d++)
				if (forward.calculate(d) ||
						backward.calculate(d))
					return edit;
		}

		EditPaths forward = new ForwardEditPaths();
		EditPaths backward = new BackwardEditPaths();

		protected int beginA
		protected Edit edit;

		abstract class EditPaths {
			private IntList x = new IntList();
			private LongList snake = new LongList();
			int beginK
			int prevBeginK
			int minK

			final int getIndex(int d
if (((d + k - middleK) % 2) == 1)
	throw new RuntimeException("odd: " + d + " + " + k + " - " + middleK);
				return (d + k - middleK) / 2;
			}

			final int getX(int d
if (k < beginK || k > endK)
	throw new RuntimeException("k " + k + " not in " + beginK + " - " + endK);
				return x.get(getIndex(d
			}

			final long getSnake(int d
if (k < beginK || k > endK)
	throw new RuntimeException("k " + k + " not in " + beginK + " - " + endK);
				return snake.get(getIndex(d
			}

			private int forceKIntoRange(int k) {
				if (k < minK)
					return minK + ((k ^ minK) & 1);
				else if (k > maxK)
					return maxK - ((k ^ maxK) & 1);
				return k;
			}

			void initialize(int k
				this.minK = minK;
				this.maxK = maxK;
				beginK = endK = middleK = k;
				this.x.clear();
				this.x.add(x);
				snake.clear();
				snake.add(newSnake(k
			}

			abstract int snake(int k
			abstract int getLeft(int x);
			abstract int getRight(int x);
			abstract boolean isBetter(int left
			abstract void adjustMinMaxK(final int k
			abstract boolean meets(int d

			final long newSnake(int k
				long y = k + x;
				long ret = ((long) x) << 32;
				return ret | y;
			}

			final int snake2x(long snake) {
				return (int) (snake >>> 32);
			}

			final int snake2y(long snake) {
				return (int) snake;
			}

			final boolean makeEdit(long snake1
				int x1 = snake2x(snake1)
				int y1 = snake2y(snake1)
				if (x1 > x2 || y1 > y2) {
					x1 = x2;
					y1 = y2;
				}
				edit = new Edit(x1
				return true;
			}

			boolean calculate(int d) {
				prevBeginK = beginK;
				prevEndK = endK;
				beginK = forceKIntoRange(middleK - d);
				endK = forceKIntoRange(middleK + d);
				for (int k = endK; k >= beginK; k -= 2) {
					int left = -1
					long leftSnake = -1L
					if (k > prevBeginK) {
						int i = getIndex(d - 1
						left = x.get(i);
						int end = snake(k - 1
						leftSnake = left != end ?
							newSnake(k - 1
							snake.get(i);
						if (meets(d
							return true;
						left = getLeft(end);
					}
					if (k < prevEndK) {
						int i = getIndex(d - 1
						right = x.get(i);
						int end = snake(k + 1
						rightSnake = right != end ?
							newSnake(k + 1
							snake.get(i);
						if (meets(d
							return true;
						right = getRight(end);
					}
					int newX;
					long newSnake;
					if (k >= prevEndK ||
							(k > prevBeginK &&
							 isBetter(left
						newX = left;
						newSnake = leftSnake;
					}
					else {
						newX = right;
						newSnake = rightSnake;
					}
					if (meets(d
						return true;
					adjustMinMaxK(k
					int i = getIndex(d
					x.set(i
					snake.set(i
				}
				return false;
			}
		}

		class ForwardEditPaths extends EditPaths {
			final int snake(int k
				for (; x < endA && k + x < endB; x++)
					if (!a.equals(x
						break;
				return x;
			}

			final int getLeft(final int x) {
				return x;
			}

			final int getRight(final int x) {
				return x + 1;
			}

			final boolean isBetter(final int left
				return left > right;
			}

			final void adjustMinMaxK(final int k
				if (x >= endA || k + x >= endB) {
					if (k > backward.middleK)
						maxK = k;
					else
						minK = k;
				}
			}

			final boolean meets(int d
				if (k < backward.beginK || k > backward.endK)
					return false;
				if (((d - 1 + k - backward.middleK) % 2) == 1)
					return false;
				if (x < backward.getX(d - 1
					return false;
				makeEdit(snake
				return true;
			}
		}

		class BackwardEditPaths extends EditPaths {
			final int snake(int k
				for (; x > beginA && k + x > beginB; x--)
					if (!a.equals(x - 1
						break;
				return x;
			}

			final int getLeft(final int x) {
				return x - 1;
			}

			final int getRight(final int x) {
				return x;
			}

			final boolean isBetter(final int left
				return left < right;
			}

			final void adjustMinMaxK(final int k
				if (x <= beginA || k + x <= beginB) {
					if (k > forward.middleK)
						maxK = k;
					else
						minK = k;
				}
			}

			final boolean meets(int d
				if (k < forward.beginK || k > forward.endK)
					return false;
				if (((d + k - forward.middleK) % 2) == 1)
					return false;
				if (x > forward.getX(d
					return false;
				makeEdit(forward.getSnake(d
				return true;
			}
		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Need 2 arguments");
			System.exit(1);
		}
		try {
			RawText a = new RawText(new java.io.File(args[0]));
			RawText b = new RawText(new java.io.File(args[1]));
			MyersDiff diff = new MyersDiff(a
			System.out.println(diff.getEdits().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
