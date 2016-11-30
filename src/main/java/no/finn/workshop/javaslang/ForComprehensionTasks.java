package no.finn.workshop.javaslang;

import java.util.function.Predicate;

import javaslang.API;
import javaslang.Function3;
import javaslang.collection.Iterator;
import javaslang.control.Either;
import javaslang.control.Option;
import javaslang.control.Try;
import no.finn.workshop.javaslang.things.Triangle;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static javaslang.API.For;

public class ForComprehensionTasks {


    // Use a javaslang For-comprehension
    // // (http://static.javadoc.io/io.javaslang/javaslang/2.0.4/index.html?javaslang/API.html)
    // to retrieve the concatenated String-value from a Success<String> (Try<String>) Right<String> (Either<Integer, String>) and Some<String> (Option<String>)
    public static String concatenate(Try<String> aTry, Either<Integer, String> anEither, Option<String> anOption) {
        /*Function3<? super String, ? super String, ? super String, ? extends String> foo = new Function3<String, String, String, String>() {
            @Override
            public String apply(String s, String s2, String s3) {
                return s + s2 + s3;
            }
        };*/
        Iterator<String> result = For(aTry, anEither, anOption).yield((s, s2, s3) -> s + s2 + s3);
        return result.getOrElse("");
    }


    // Use a javaslang For-comprehension
    // (http://static.javadoc.io/io.javaslang/javaslang/2.0.4/index.html?javaslang/API.html)
    // to return an iterable over all RIGHT rectangles where the sum of the sides are <= 25
    // you are given an iterator over values for each side (a,b,c) in the triangle
    // use the things.Triangle helper class to create a new Triangle object
    // remember to put pythagoras in there somewhere
    // (c is always the hypotenus)
    public static Iterable<Triangle> combinations(Iterable<Double> a, Iterable<Double> b, Iterable<Double> c) {
        /*Function3<? super Double, ? super Double, ? super Double, ? extends Triangle> foo = new Function3<Double, Double, Double, Triangle>() {
            @Override
            public Triangle apply(Double aDouble, Double aDouble2, Double aDouble3) {
                return new Triangle(aDouble, aDouble2, aDouble3);
            }
        };
        Predicate<? super Triangle> bar = new Predicate<Triangle>() {
            @Override
            public boolean test(Triangle t) {
                return t.a() * t.a() + t.b() * t.b() == t.c() * t.c();
            }
        };*/
        return For(a, b, c).yield(Triangle::new)
                .filter(t -> t.a() * t.a() + t.b() * t.b() == t.c() * t.c())
                .filter(t -> t.a() > 0 && t.b() > 0 && t.c() > 0);
    }


}
