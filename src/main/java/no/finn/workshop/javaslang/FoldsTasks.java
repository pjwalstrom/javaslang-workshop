package no.finn.workshop.javaslang;

import java.util.function.BiFunction;

import javaslang.collection.List;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FoldsTasks {

    public static Double sumAListUsingFold(List<Double> list) {
        /*BiFunction<? super Double, ? super Double, ? extends Double> func = new BiFunction<Double, Double, Double>() {
            @Override
            public Double apply(Double aDouble, Double aDouble2) {
                return aDouble + aDouble2;
            }
        };*/

        return list.fold(0D, (d1, d2) -> d1 + d2);
    }

    //Reverse a list using foldRight
    public static <X> List<X> reverseRight(List<X> original) {
        /*BiFunction<? super X, ? super List<X>, ? extends List<X>> foo = new BiFunction<X, List<X>, List<X>>() {
            @Override
            public List<X> apply(X x, List<X> xes) {
                return xes.append(x);
            }
        };*/
        return original.foldRight(List.empty(), (x, xes) -> xes.append(x));
    }

    //Reverse a list using foldLeft
    public static <X> List<X> reverseLeft(List<X> original) {

        //return original.foldLeft(List.empty(), (xes, x) -> xes.prepend(x));
        return original.foldLeft(List.empty(), List::prepend);
    }

}
