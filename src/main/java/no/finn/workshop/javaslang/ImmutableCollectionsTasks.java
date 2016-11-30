package no.finn.workshop.javaslang;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javaslang.Function1;
import javaslang.Tuple2;
import javaslang.collection.Array;
import javaslang.collection.HashMap;
import javaslang.collection.HashSet;
import javaslang.collection.List;
import javaslang.collection.Stream;
import javaslang.collection.TreeSet;
import javaslang.collection.Vector;
import no.finn.workshop.javaslang.things.Age;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ImmutableCollectionsTasks {
    private static final Logger LOG = LoggerFactory.getLogger(ImmutableCollectionsTasks.class);

    //List
    public static <T> List<T> createFrom(java.util.List<T> aList) {
        return List.ofAll(aList);
    }

    //List
    public static <T> List<T> createFromAll(T... objects) {
        return List.of(objects);
    }

    //turn every element of the list into an Age-object
    public static List<Age>  transfomElements(List<Integer> list) {
        /*Function<? super Integer, Age> foo = new Function<Integer, Age>() {
            @Override
            public Age apply(Integer integer) {
                return new Age(integer);
            }
        };*/
        //return List.ofAll(list.toStream().map(Age::new));
        return list.map(Age::new);
    }

    //use the corresponds-method to check that two lists contain the same String when ignoring case
    public static Boolean checkSameStringsIgnoreCase(List<String> first, List<String> second) {

        /*BiPredicate<? super String, ? super String> foo = new BiPredicate<String, String>() {
            @Override
            public boolean test(String s, String s2) {
                return s.equalsIgnoreCase(s2);
            }
        };*/
        //return first.mkString().equalsIgnoreCase(second.mkString());
        return first.corresponds(second, String::equalsIgnoreCase);
    }

    //create a new Tuple2 from arguments
    public static Tuple2<String, Integer> createTupleFrom(String a, Integer b) {
        return new Tuple2<>(a, b);
    }

    //how to call a function for every entry?
    public static List<Integer> doubleEveryInt(List<Integer> ints) {
        return ints.map(i -> i * 2);
    }

    //how to keep some values and drop some values based on a predicate?
    public static List<Integer> retainOddNumbers(List<Integer> ints) {
        return ints.filter(i -> i % 2 != 0);
    }

    //flip every other (element that has even index (0 based)) tuple in a list. zipWithIndex might come in handy.
    public static List<Tuple2<Integer, Integer>> flipEveryOther(List<Tuple2<Integer, Integer>> ints) {
        /*LOG.info("ints received: {}", ints.toString());
        List<Tuple2<Tuple2<Integer, Integer>, Long>> tuple2s = ints.zipWithIndex();
        LOG.info("tuples: {}", tuple2s);

        Function<? super Tuple2<Tuple2<Integer, Integer>, Long>, Tuple2<Integer, Integer>> foo = new Function<Tuple2<Tuple2<Integer, Integer>, Long>, Tuple2<Integer, Integer>>() {
            @Override
            public Tuple2<Integer, Integer> apply(Tuple2<Tuple2<Integer, Integer>, Long> tuple2) {
                LOG.info(tuple2.toString());
                if (tuple2._2 % 2 == 0) {
                    LOG.info("should flip {}", tuple2);
                    return new Tuple2<>(tuple2._1._2, tuple2._1._1);
                }
                return tuple2._1;
            }
        };*/



        //return ints.zipWithIndex().map(t -> t._2 % 2 == 0 ? new Tuple2<>(t._1._2, t._1._1) : t._1);
        //BiFunction<? super Tuple2<Integer, Integer>, ? super Long, Tuple2<Integer, Integer>> foo = (tuple, index) -> index % 2 == 0 ? new Tuple2<>(tuple._2(), tuple._1()) : tuple;
        return ints.zipWithIndex().map(t -> t.map((tuple, index) -> index % 2 == 0 ? new Tuple2<>(tuple._2(), tuple._1()) : tuple));
    }

    //flip a tuple
    private static <A,B> Tuple2<B,A> flip(Tuple2<A,B> t) {
        throw new NotImplementedException();
    }

    //Make this function return a Tuple2(a,b+10) (keep first element (String) and add 10 to second element (Integer))
    public static Tuple2<String, Integer> transformTuple(Tuple2<String, Integer> theTuple) {
        /*Function<? super Integer, ? extends Integer> foo = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer + 10;
            }
        };*/
        return theTuple.map2(i -> i + 10);
    }

    //create a javaslang HashMap
    public static <A,B> HashMap<A,B> toJavaslangMap(java.util.Map<? extends A, ? extends B> map) {
        return HashMap.ofAll(map);
    }

    //create a javaslang HashSet
    public static <A> javaslang.collection.HashSet<A> toJavaslangSet(java.util.Set<A> set) {
        return HashSet.ofAll(set);
    }

    //create a java.util.List
    public static <A> java.util.List<A> toJavaList(List<A> list) {
        return list.toJavaList();
    }

    //create a javaslang Stream of increasing integers from (inclusive) to (inclusive) given start and end-points
    public static Stream<Integer> createStream(Integer fromInclusive, Integer toInclusive) {
        return Stream.rangeClosed(fromInclusive, toInclusive);
    }

    //create a javaslang List from a java.util.streamStream
    public static <V> List<V> toJavaslangList(java.util.stream.Stream<V> stream) {
        return stream.collect(List.collector());
        //return List.ofAll(stream.collect(Collectors.toList()));
    }

    // Should return a Function1 from Value V to Boolean. True when V exists in set, False otherwise
    public static <V> Function1<V, Boolean> toFunction(javaslang.collection.HashSet<V> set) {
        /*Function1<V, Boolean> foo = new Function1<V, Boolean>() {
            @Override
            public Boolean apply(V v) {
                return set.contains(v);
            }
        };*/
        //return set::contains;
        return set;
    }

    // Should return a Function1 from Value V to Key K
    public static <K,V> Function1<K,V> toFunction(javaslang.collection.HashMap<K,V> map) {
        /*Function1<K, V> foo = new Function1<K, V>() {
            @Override
            public V apply(K k) {
                return map.get(k).get();
            }
        };*/

        //return map::apply;
        return map;
    }

    // Sum ints in a javaslang HashSet
    public static Integer sum(javaslang.collection.HashSet<Integer> ints) {
        return ints.sum().intValue();
    }

    // make a javaslang list from a javaslang set
    public static <A> List<A> toList(javaslang.collection.HashSet<A> set) {
        return List.ofAll(set);
    }

    // make a javaslang Vector of doubles
    // Vectors - are balanced when it comes to performance of insert, delete and query
    public static Vector<Double> toVector(Double... doubles) {
        return Vector.of(doubles);
    }

    // make a javaslang Array of the english alfabet
    // Arrays, unlike List can insert and delete values in constant time
    public static Array<Character> alfabet() {
        return Array.rangeClosed('a', 'z');
    }

    // Sort characters using the javaslang TreeSet
    public static List<Character> sort(Character... chars) {
        return TreeSet.of(chars).toList();
    }

    // Find the intersection between two trees
    public static <X> TreeSet<X> intersection(TreeSet<X> first, TreeSet<X> second) {
        return first.intersect(second);
    }

    // split a tree using a predicate
    public static <X> Tuple2<TreeSet<X>, TreeSet<X>> split(TreeSet<X> tree, Predicate<X> predicate) {
        return tree.partition(predicate);
    }

}
