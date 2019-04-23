import pojo.Employee;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Generics {


    public static void main(String args[]) {

        /**
         One of the challenges of working on a language like Java is that it needs to support years of backward compatibility. When generics were added to the language, the decision was made to remove them during the compilation process. That way no new classes are created for parameterized types, so there is no runtime penalty to using them.

         Since all of that is done under the hood, all you really need to know is that at compile time:

         Bounded type parameters are replaced with their bounds

         Unbounded type parameters are replaced with Object

         Type casts are inserted where needed

         Bridge methods are generated to preserve polymorphism
         */

        System.out.println("-------------------------------");
        System.out.println("Generics using List");
        System.out.println("-------------------------------");
        usingList();
        System.out.println("-------------------------------");
        System.out.println("Generics using Map");
        System.out.println("-------------------------------");
        usingMap();
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
        System.out.println("SLOWER IntStream integer generics traversing without auto boxing");
        List<Integer> numList1 = new ArrayList<>();
        LocalDateTime dttmStart = LocalDateTime.now();
        IntStream.rangeClosed(1, 100000).forEach(i -> numList1.add(new Integer(i)));
        numList1.forEach(System.out::print);
        System.out.println();
        System.out.println(ChronoUnit.MILLIS.between(dttmStart, LocalDateTime.now()));


        System.out.println();
        System.out.println("FASTER IntStream integer generics traversing with auto boxing int");
        dttmStart = LocalDateTime.now();
        List<Integer> numList2 = new ArrayList<>();
        IntStream.rangeClosed(1, 100000).forEach(i -> numList2.add(i));
        numList2.forEach(System.out::print);
        System.out.println();
        System.out.println(ChronoUnit.MILLIS.between(dttmStart, LocalDateTime.now()));


        System.out.println();
        System.out.println("nCopies integer generics traversing");
        List<Integer> numList3 = new ArrayList<>();
        dttmStart = LocalDateTime.now();
        Collections.nCopies(100000, 1).stream().forEach(i -> numList3.add(i));
        numList3.forEach(System.out::print);
        System.out.println();
        System.out.println(ChronoUnit.MILLIS.between(dttmStart, LocalDateTime.now()));

        System.out.println();
        System.out.println("Check bounded types");
        System.out.println("generic list not bounded to generic type");
        strList1.addAll(strList2);
        Collections.addAll(strList1, "ABC", 123, "PQR");
        strList1.forEach(System.out::println);
        System.out.println("String list bounded to String type only: compile time error");
        // Collections.addAll(strList2, "ABC", 123, "PQR"); // compile time casting exception
        // strList2.addAll(strList1); // compile time casting exception
        strList2.forEach(System.out::println);


        System.out.println();
        System.out.println("Upper bounded wildcards");
        List<? extends Number> uBoundList = new ArrayList<>();
        System.out.println("can define the list with the upper bounded wildcard, again you can’t add to it");
        System.out.println("can define a method argument and then invoke the method with the different types of lists");
//        uBoundList.add(new Integer(1));
//        uBoundList.add(3.14159);
//        uBoundList.add(new BigDecimal(1234.1234));

        List<Integer> intList = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        List<Double> doubleList = Arrays.asList(1D, 2D, 3D, 4D, 5D, 6D, 7D, 8D, 9D, 10D);
        List<BigDecimal> bigList = Arrays.asList(new BigDecimal(1), new BigDecimal(2));
        System.out.println("Sum of list: " + usingUpperBoundedWildcard(intList));
        System.out.println("Sum of list: " + usingUpperBoundedWildcard(doubleList));
        System.out.println("Sum of list: " + usingUpperBoundedWildcard(bigList));

        System.out.println();
        System.out.println("Lower bounded wildcards");
        List<Integer> intListLb = new ArrayList<>();
        List<Number> numListLb = new ArrayList<>();
        List<Object> objListLb = new ArrayList<>();
        usingLowerBoundedWildcard(5, intListLb);
        usingLowerBoundedWildcard(5, numListLb);
        usingLowerBoundedWildcard(5, objListLb);
        System.out.println("List of Integer: ");
        intListLb.forEach(System.out::println);
        System.out.println("List of Number: ");
        numListLb.forEach(System.out::println);
        System.out.println("List of Objects: ");
        objListLb.forEach(System.out::println);

        List<Employee> empList = Arrays.asList(
                new Employee(1, "Name1"),
                new Employee(2, "Name2"),
                new Employee(3, "Name3"),
                new Employee(4, "Name4")
        );
        Employee minIdEmp = empList.stream().min(Comparator.comparingInt(Employee::getId)).orElse(Employee.DEF_EMP);
        Employee maxNameEmp = empList.stream().max(Comparator.comparing(Employee::getName)).orElse(Employee.DEF_EMP);
        System.out.println("Min Id Employee: " + minIdEmp);
        System.out.println("Max Name Employee: " + maxNameEmp);

        System.out.println();
        System.out.println("Producer Extends GET, Consumer Super PUT");
        System.out.println("covariant preserves the ordering of types from more specific to more general");
        System.out.println("contravariant preserves the ordering of types from more general to more specific");
        System.out.println("invariant means that the type must be exactly as specified");
        List<String> empNames1 = empList.stream().map(Employee::getName).collect(Collectors.toList());
        List<String> empNames2 = empList.stream().map(Objects::toString).collect(Collectors.toList());
        System.out.println("List of emp names: ");
        empNames1.forEach(System.out::println);
        System.out.println("List of obj toString: ");
        empNames2.forEach(System.out::println);

        System.out.println();
        System.out.println("Multiple bounds wildcards separated by an ampersand");
        System.out.println("can have as many interface bounds as you like, but only one can be a class." +
                " If you have a class as a bound, it must be first in the list.");

        // strList1 = strList2; // compile time error
        strList0 = strList2;
        strList0.add(123);
        strList0.forEach(System.out::println);
        strList2.forEach(System.out::println); // run time casting exception
    }

    // argument type limited upto class inherited from Number class e.g. Integer, Double, BigDecimal etc.
    private static double usingUpperBoundedWildcard(List<? extends Number> numList) {
        return numList.stream().mapToDouble(Number::doubleValue).sum();
    }

    // argument type limited from class Integer and its super classes i.e. Number, Object
    private static void usingLowerBoundedWildcard(Integer num, List<? super Integer> list) {
        IntStream.rangeClosed(1, num).forEach(list::add);
    }

    private static void usingMap() {

    }
}