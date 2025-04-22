package org.eclipse.jface.tests.viewers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.jface.viewers.deferred.LazySortedCollection;

public class LazySortedCollectionTest extends TestCase {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(LazySortedCollectionTest.class);
    }

    public static Test suite() {
        return new TestSuite(LazySortedCollectionTest.class);
    }

    private TestComparator comparator;
    private TestComparator comparisonComparator;

    private LazySortedCollection collection;
    private TreeSet comparisonCollection;

    private String[] se = new String[] {
            "v00 aaaaaa",
            "v01 apple",
            "v02 booger",
            "v03 car",
            "v04 dog",
            "v05 elephant",
            "v06 fox",
            "v07 goose",
            "v08 hippie",
            "v09 iguana",
            "v10 junk",
            "v11 karma",
            "v12 lemon",
            "v13 mongoose",
            "v14 noodle",
            "v15 opal",
            "v16 pumpkin",
            "v17 quirks",
            "v18 resteraunt",
            "v19 soap",
            "v20 timmy",
            "v21 ugly",
            "v22 virus",
            "v23 wigwam",
            "v24 xerxes",
            "v25 yellow",
            "v26 zero"
    };

    private String[] elements = new String[] {
            se[19],
            se[7],
            se[6],
            se[1],
            se[20],
            se[8],
            se[0],
            se[23],
            se[17],
            se[18],
            se[24],
            se[25],
            se[10],
            se[5],
            se[15],
            se[16],
            se[21],
            se[26],
            se[22],
            se[3],
            se[9],
            se[4],
            se[11],
            se[12],
            se[13],
            se[14],
            se[2]
        };


    public static void printArray(Object[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println("[" + i + "] = " + array[i]);
        }
    }

    @Override
	protected void setUp() throws Exception {
        System.out.println("--- " + getName());

        comparator = new TestComparator();
        collection = new LazySortedCollection(comparator);
        collection.enableDebug = true;

        comparisonComparator = new TestComparator();
        comparisonCollection = new TreeSet(comparisonComparator);

        addAll(elements);

        super.setUp();
    }

    @Override
	protected void tearDown() throws Exception {
        System.out.println("Comparisons required by lazy collection: " + comparator.comparisons);
        System.out.println("Comparisons required by reference implementation: " + comparisonComparator.comparisons);
        System.out.println("");

        super.tearDown();
    }

    private Object[] computeExpectedElementsInRange(int start, int length) {
        int counter = 0;

        Iterator iter = comparisonCollection.iterator();
        while(iter.hasNext() && counter < start) {
            iter.next();
            counter++;
        }

        Object[] result = new Object[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = iter.next();
        }

        return result;
    }

    private void addAll(Object[] elements) {
        collection.addAll(elements);
        comparisonCollection.addAll(Arrays.asList(elements));
    }

    private void add(Object toAdd) {
        collection.add(toAdd);
        comparisonCollection.add(toAdd);
    }

    private void remove(Object toRemove) {
        collection.remove(toRemove);
        comparisonCollection.remove(toRemove);
    }

    private void removeRange(int start, int length) {
        collection.removeRange(start, length);

        Object[] expected = computeExpectedElementsInRange(start, length);

        for (Object element : expected) {
            comparisonCollection.remove(element);
        }
    }

    private void clear() {
        collection.clear();
        comparisonCollection.clear();
    }

    private void forceFullSort() {
        queryRange(0, elements.length, true);
    }

    private void assertContentsValid() {
        queryRange(0, comparisonCollection.size(), false);
        Assert.assertEquals(comparisonCollection.size(), collection.size());
        Assert.assertEquals(comparisonCollection.isEmpty(), collection.isEmpty());
    }

    private void assertIsPermutation(Object[] array1, Object[] array2) {
        Object[] sorted1 = new Object[array1.length];
        System.arraycopy(array1, 0, sorted1, 0, array1.length);
        Arrays.sort(sorted1, new TestComparator());

        Object[] sorted2 = new Object[array2.length];
        System.arraycopy(array2, 0, sorted2, 0, array2.length);
        Arrays.sort(sorted2, new TestComparator());

        assertArrayEquals(sorted1, sorted2);
    }

    private Object[] queryRange(int start, int length, boolean sorted) {
        Object[] result = new Object[length];

        int returnValue = collection.getRange(result, start, sorted);

        Assert.assertEquals(returnValue, length);

        Object[] expectedResult = computeExpectedElementsInRange(start, length);

        if (sorted) {
            assertArrayEquals(expectedResult, result);
        } else {
            assertIsPermutation(result, expectedResult);
        }

        collection.testInvariants();

        return result;
    }

    private void assertArrayEquals(Object[] array1, Object[] array2) {
        for (int i = 0; i < array1.length; i++) {

            Assert.assertEquals(array1[i], array2[i]);
        }
    }

    public void testComparisonCount() {
        Assert.assertTrue("additions should not require any comparisons", comparator.comparisons == 0);

        queryRange(0, elements.length, false);

        Assert.assertTrue("requesting the complete set of unsorted elements should not require any comparisons", comparator.comparisons == 0);
    }

    public void testSortAll() {
        queryRange(0, elements.length, true);

        int comparisons = comparator.comparisons;

        queryRange(elements.length - 10, 10, true);
        queryRange(0, 10, false);

        Assert.assertEquals("Once the lazy collection is fully sorted, it should not require further comparisons",
                comparisons, comparator.comparisons);
    }

    public void testRemoveLeafNode() {
        forceFullSort();
        remove(se[9]);
        assertContentsValid();
    }

    public void testRemoveNodeWithNoLeftChild() {
        forceFullSort();
        remove(se[23]);
        assertContentsValid();
    }

    public void testRemoveNodeWithNoRightChild() {
        forceFullSort();
        remove(se[13]);
        assertContentsValid();
    }

    public void testRemoveRootNode() {
        forceFullSort();
        remove(se[19]);
        assertContentsValid();
    }

    public void testRemoveWhereSwappedNodeIsntLeaf() {
        forceFullSort();
        remove(se[14]);
        assertContentsValid();
    }

    public void testRemoveWithUnsortedSwap() {
        queryRange(14, 1, true);
        queryRange(13, 1, true);

        addAll(new String[] {"v13 n", "v13 l"});
        queryRange(8, 1, true);

        addAll(new String[] {"v14 m", "v14 o"});
        queryRange(7, 1, true);


        remove(se[14]);
        assertContentsValid();
    }

    public void testRemoveFromUnsorted() {
        remove(se[10]);
        assertContentsValid();
    }

    public void testRemoveRootFromUnsorted() {
        remove(se[19]);
        assertContentsValid();
    }

    public void testRemoveUnknown() {
        remove("some unknown element");
        assertContentsValid();
    }

    public void testRemovePreviouslySwappedNode() {
        queryRange(0, elements.length, true);
        remove(se[14]);
        add("something new");
        assertContentsValid();
        remove(se[13]);
        assertContentsValid();
    }

    public void testRemoveFullRange() {
        removeRange(0, se.length);
        assertContentsValid();
    }

    public void testRemoveFromStart() {
        removeRange(0, se.length / 2);
        assertContentsValid();
    }

    public void testRemoveFromEnd() {
        int start = se.length / 2;
        removeRange(start, se.length - start);
        assertContentsValid();
    }

    public void testRemoveIncludingRoot() {
        removeRange(14, 6);
        assertContentsValid();
    }

    public void testRemoveRightSubtree() {
        removeRange(9, 5);
        assertContentsValid();
    }

    public void testRemoveLeftSubtree() {
        removeRange(3, 4);
        assertContentsValid();
    }

    public void testRemoveRightIncludingRoot() {
        removeRange(3, 5);
        assertContentsValid();
    }

    public void testClear() {
        clear();
        assertContentsValid();
    }

    public void testClearSorted() {
        forceFullSort();
        clear();
        assertContentsValid();
    }

}
