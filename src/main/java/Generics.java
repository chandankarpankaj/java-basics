import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Generics {
    public static void main(String args[]) {
        System.out.println("Hello World!!!");
        usingList();
    }

    private static void usingList() {

        // basic without generics
        System.out.println();
        System.out.println("Basic without generics");
        List strList0 = new ArrayList<>();
        strList0.add("ABC");
        strList0.add("PQR");
        strList0.add(123);

        // with generics Object class
        System.out.println();
        System.out.println("with Generics Object class");
        List<Object> strList1 = new ArrayList<>();
        strList1.add("ABC");
        strList1.add("PQR");
        strList1.add(123);

        strList1.forEach(System.out::println);

        // with generics String class
        System.out.println();
        System.out.println("with Generics String class");
        List<String> strList2 = new ArrayList<>();
        strList2.add("ABC");
        strList2.add("PQR");
        strList2.forEach(System.out::println);

        System.out.println();
        System.out.println("SLOWER IntStream integer generics without auto boxing");
        List<Integer> numList1 = new ArrayList<>();
        LocalDateTime dttmStart = LocalDateTime.now();
        IntStream.rangeClosed(1, 100000).forEach(i -> numList1.add(new Integer(i)));
        numList1.forEach(System.out::print);
        System.out.println();
        System.out.println(ChronoUnit.MILLIS.between(dttmStart, LocalDateTime.now()));


        System.out.println();
        System.out.println("FASTER IntStream integer generics with auto boxing int");
        dttmStart = LocalDateTime.now();
        List<Integer> numList2 = new ArrayList<>();
        IntStream.rangeClosed(1, 100000).forEach(i -> numList2.add(i));
        numList2.forEach(System.out::print);
        System.out.println();
        System.out.println(ChronoUnit.MILLIS.between(dttmStart, LocalDateTime.now()));


        System.out.println();
        System.out.println("nCopies integer generics");
        List<Integer> numList3 = new ArrayList<>();
        dttmStart = LocalDateTime.now();
        Collections.nCopies(100000, 1).stream().forEach(i -> numList3.add(i));
        numList3.forEach(System.out::print);
        System.out.println();
        System.out.println(ChronoUnit.MILLIS.between(dttmStart, LocalDateTime.now()));

        System.out.println();
        System.out.println("Check bounded types");
        System.out.println("generic list not bounded to any");
        strList1.addAll(strList2);
        Collections.addAll(strList1, "ABC", 123, "PQR");
        strList1.forEach(System.out::println);
        System.out.println("String list bounded to String collection: runtime casting exception");
        // Collections.addAll(strList2, "ABC", 123, "PQR"); // compile time casting exception
        // strList2.addAll(strList1); // compile time casting exception
        strList2.forEach(System.out::println);

        // strList1 = strList2; // compile time error
        strList0 = strList2;
        strList0.add(123);
        strList0.forEach(System.out::println);
        strList2.forEach(System.out::println); // run time casting exception
    }
}