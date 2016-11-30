package no.finn.workshop.javaslang;

import java.util.function.Function;

import javaslang.collection.List;
import javaslang.collection.Seq;
import javaslang.control.Either;
import javaslang.control.Option;
import javaslang.control.Try;
import no.finn.workshop.javaslang.things.Person;
import no.finn.workshop.javaslang.things.PersonServiceImpl;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import sun.tools.asm.TryData;

public class TryEitherOptionTasks {

    //Create an Either.Left of an empty Option<String>
    public static Either<Option<String>, Option<Integer>> weirdEither() {
        return Either.left(Option.none());
    }

    //Create a Success (Try) of type Option(4) (Option<Integer>)
    public static Try<Option<Integer>> maybeIntegerAttempt() {
        return Try.success(Option.of(4));
    }

    //Transform a Try to an Option that is Present on Success and absent on Failure
    public static <A> Option<A> toOption(Try<A> tryy) {
        //Option<A> foo = Option.when(tryy.isSuccess(), tryy.get());
        //return Option.when(tryy.isSuccess(), tryy.getOrElseThrow(t -> new RuntimeException()));
        return tryy.toOption();
    }

    //From a List of personId, use a new PersonService() to return a list of corresponding Addresses (a String)
    //Use an empty String for every missing or failing Address
    public static List<String> getAddresses(List<Long> personIds) {
        PersonServiceImpl personService = new PersonServiceImpl();
        /*Function<? super Long, ? extends String> foo = new Function<Long, String>() {
            @Override
            public String apply(Long aLong) {
                String res;
                Either<String, Option<Person>> person = personService.getPerson(aLong);
                if (person.isLeft() || person.get().isEmpty()) {
                    res = "";
                } else {
                    res = personService.getAddress(person.get().get());
                }
                return res;
            }
        };   */

        return personIds.map(personService::getPerson).map(e -> e.fold(s -> "", o -> o.map(personService::getAddress).getOrElse("")));

    }

    // apply two unsafe functions and return the Try of the result (first apply seed to unsafeFunction1, then apply result to unsafeFunction2)
    public static Try<String> combineUnsafeFunctions(Function<Integer, Integer> unsafeFunction1, Function<Integer, String> unsafeFunction2, Integer seed) {
        /*Try.CheckedSupplier<? extends String> foo = new Try.CheckedSupplier<String>() {
            @Override
            public String get() throws Throwable {
                return unsafeFunction2.apply(unsafeFunction1.apply(seed));
            }
        };*/
        return Try.of(() -> unsafeFunction1.apply(seed)).map(unsafeFunction2);
        //return Try.of(() -> unsafeFunction2.apply(unsafeFunction1.apply(seed)));
    }

    // Do the exact same thing, but now use flatMap() if you used map() earlier (or vice versa)
    public static Try<String> combineUnsafeFunctions2(Function<Integer, Integer> unsafeFunction1, Function<Integer, String> unsafeFunction2, Integer seed) {
        return Try.of(() -> unsafeFunction2.apply(unsafeFunction1.apply(seed)));
    }

    //Given a bunch of tries, construct a Try of a Seq (javaslang sequence) that is a success if the whole bunch is a success,
    // or short-circuits to a Failure if one or more in the bunch is a Failure
    public static <A> Try<Seq<A>> superTry(List<Try<? extends A>> tries) {
        return Try.sequence(tries);
    }
}
